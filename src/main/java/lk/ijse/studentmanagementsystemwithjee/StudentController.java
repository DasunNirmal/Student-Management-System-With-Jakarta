package lk.ijse.studentmanagementsystemwithjee;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/Student")
public class StudentController extends HttpServlet {

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

        /*JSON Manipulation with Parson*/
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.getString("name"));
        System.out.println(jsonObject.getString("city"));
        System.out.println(jsonObject.getString("email"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Get Student details*/
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Update Student details*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:Delete Student details*/
    }

}
