package lk.ijse.studentmanagementsystemwithjee.persistance;

import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;

import java.sql.Connection;

public interface Data {
    StudentDTO getStudent(String id, Connection connection);
    String saveStudent(StudentDTO studentDTO, Connection connection);
    boolean deleteStudent(String id, Connection connection);
    boolean updateStudent(String id, StudentDTO studentDTO, Connection connection);
}
