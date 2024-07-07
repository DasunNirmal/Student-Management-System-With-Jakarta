package lk.ijse.studentmanagementsystemwithjee.controller;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;
import lk.ijse.studentmanagementsystemwithjee.entity.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {

    Connection connection;

    static String SAVE_STUDENT = "INSERT INTO Student VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM Student WHERE id = ?";

    @Override
    public void init() throws ServletException {
        try {

            var driver = getServletContext().getInitParameter("driver-class");
            var url = getServletContext().getInitParameter("dbURL");
            var user = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Save Student details*/

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        /*Process*/
        /*BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        PrintWriter writer = resp.getWriter();
        reader.lines().forEach(line -> sb.append(line).append(line + "\n"));
        System.out.println(sb);
        writer.write(sb.toString());
        writer.close();*/

        /*JSON Manipulation with Parson(parson is a library)*/
        /*JsonReader reader = Json.createReader(req.getReader());
        JsonObject jObject = reader.readObject();
        System.out.println(jObject.getString("name"));
        System.out.println(jObject.getString("city"));
        System.out.println(jObject.getString("email"));*/

        /*JSON Manipulation with Parson(parson is a library) using Array*/
        /*JsonArray jsonArray = reader.readArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            System.out.println(jsonObject.getString("name"));
            System.out.println(jsonObject.getString("city"));
            System.out.println(jsonObject.getString("email"));
        }*/

        /*JSON Manipulation with Yasson(parson is a library)*/
        String id = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class); //this binds the values to the object
        studentDTO.setId(id);
        System.out.println(studentDTO);

        /*JSON Manipulation with Yasson(parson is a library) using Array*/
        /*Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO> () {
        }.getClass().getGenericSuperclass()); //this binds the values to the Array
        studentDTOList.forEach(System.out::println);*/

        /*Persist Data to the Database using mysql*/
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(5, studentDTO.getLevel());

            if(ps.executeUpdate() != 0 ) {
                resp.getWriter().write("Student Saved");
            } else {
                resp.getWriter().write("Student Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Get Student details*/

        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setLevel(resultSet.getString("level"));
            }
            System.out.print(studentDTO);
            /*resp.getWriter().write(studentDTO.toString());*/ /*this will print as a system out on the interface(postman app)*/
            resp.setContentType("application/json"); /*this tells the server to the sending value is a Jason type*/
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(studentDTO, resp.getWriter()); /*because this values need to be printed to the front end we need to pass the Writer rather than Reader*/

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Update Student details*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Delete Student details*/
    }

}
