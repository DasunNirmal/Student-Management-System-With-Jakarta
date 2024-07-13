package lk.ijse.studentmanagementsystemwithjee.persistance;

import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public class DataProcess implements Data {

    static String SAVE_STUDENT = "INSERT INTO Student VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM Student WHERE id = ?";
    static String UPDATE_STUDENT = "UPDATE Student SET name = ?, email = ?, city = ?, level = ? WHERE id = ?";
    static String DELETE_STUDENT = "DELETE FROM Student WHERE id = ?";

    @Override
    public StudentDTO getStudent(String id, Connection connection) {
        var studentDTO = new StudentDTO();
        try {
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setLevel(resultSet.getString("level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentDTO;
    }

    @Override
    public String saveStudent(StudentDTO studentDTO, Connection connection) {
        return "";
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) {
        return false;
    }

    @Override
    public boolean updateStudent(String id, StudentDTO studentDTO, Connection connection) {
        return false;
    }
}
