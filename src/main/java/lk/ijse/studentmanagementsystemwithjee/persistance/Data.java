package lk.ijse.studentmanagementsystemwithjee.persistance;

import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface Data {
    StudentDTO getStudent(String id, Connection connection);
    String saveStudent(StudentDTO studentDTO, Connection connection);
    boolean deleteStudent(String id, Connection connection) throws SQLException;
    boolean updateStudent(String id, StudentDTO studentDTO, Connection connection) throws SQLException;
}
