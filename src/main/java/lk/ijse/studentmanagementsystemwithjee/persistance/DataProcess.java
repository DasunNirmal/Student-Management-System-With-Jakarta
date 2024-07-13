package lk.ijse.studentmanagementsystemwithjee.persistance;

import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;

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
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(5, studentDTO.getLevel());

            if(ps.executeUpdate() != 0 ) {
                return "Student Saved";
                /*resp.setStatus(HttpServletResponse.SC_CREATED);*/ /*this will return 201 as status code means he request succeeded, and a new resource was created.*/
            } else {
                return "Student Not Saved";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) throws SQLException {
        var ps = connection.prepareStatement(DELETE_STUDENT);
        try {
            ps.setString(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps.executeUpdate() != 0;
    }

    @Override
    public int updateStudent(String id, StudentDTO studentDTO, Connection connection) throws SQLException {
        var ps = connection.prepareStatement(UPDATE_STUDENT);

        try {
            ps.setString(1, studentDTO.getName());
            ps.setString(2, studentDTO.getEmail());
            ps.setString(3, studentDTO.getCity());
            ps.setString(4, studentDTO.getLevel());
            ps.setString(5, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps.executeUpdate();
    }
}
