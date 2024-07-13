package lk.ijse.studentmanagementsystemwithjee.controller;

import jakarta.json.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;
import lk.ijse.studentmanagementsystemwithjee.entity.Student;
import lk.ijse.studentmanagementsystemwithjee.persistance.DataProcess;
import lk.ijse.studentmanagementsystemwithjee.util.UtilProcess;

import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = "/student")
/*initParams ={
        @WebInitParam(name = "driver-class", value = "com.mysql.cj.jdbc.Driver"),
        @WebInitParam(name = "dbURL", value = "jdbc:mysql://localhost:3306/JakarthaEE?createDatabaseIfNotExist=true"),
        @WebInitParam(name = "dbUserName", value = "root"),
        @WebInitParam(name = "dbPassword", value = "Ijse@1234")
}*/
public class StudentController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {

        try {
            var driver = getServletContext().getInitParameter("driver-class"); /*type inference*/
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

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) { /*checks if it's a jason type using header*/
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

        /*JSON Manipulation with Yasson(parson is a library) autogenerated id*/
        /*Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class); //this binds the values to the object
        studentDTO.setId(UtilProcess.generateID());
        System.out.println(studentDTO);*/

        /*JSON Manipulation with Yasson(parson is a library) using Array*/
        /*Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO> () {
        }.getClass().getGenericSuperclass()); //this binds the values to the Array
        studentDTOList.forEach(System.out::println);*/

        /*Persist Data to the Database using mysql*/
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
            studentDTO.setId(UtilProcess.generateID());
            var saveData = new DataProcess();
            writer.write(saveData.saveStudent(studentDTO, connection));
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Get Student details*/

        var studentId = req.getParameter("id");
        DataProcess dataProcess = new DataProcess();

        try (var writer = resp.getWriter()){
            var student = dataProcess.getStudent(studentId, connection);
            System.out.println(student);
            resp.setContentType("application/json"); /*this tells the server to the sending value is a Jason type*/
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(student, writer);/*this will print as a system out on the interface(postman app) because this values need to be printed to the front end we need to pass the Writer rather than Reader*/
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Update Student details*/

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) { /*checks if it's a jason type using header*/
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        DataProcess dataProcess = new DataProcess();
        try(var writer = resp.getWriter()) {
            var studentID = req.getParameter("stu-id");
            Jsonb jsonb = JsonbBuilder.create();
            var studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
            boolean rowsAffected = dataProcess.updateStudent(studentID, studentDTO, connection);
            if(rowsAffected) {
                resp.getWriter().write("Student Updated");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Update Failed");
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Delete Student details*/

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) { /*checks if it's a jason type using header*/
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        var studentID = req.getParameter("stu-id");
        try {
            boolean deleteStudent = new DataProcess().deleteStudent(studentID, connection);

            if (deleteStudent) {
                resp.getWriter().write("Student Deleted");
                resp.sendError(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Student Not Deleted");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

