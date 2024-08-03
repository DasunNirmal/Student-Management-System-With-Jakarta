package lk.ijse.studentmanagementsystemwithjee.persistance;

import lk.ijse.studentmanagementsystemwithjee.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface Data permits DataProcess { /*the permits allows us to extend a class only once*/
    StudentDTO getStudent(String id, Connection connection);
    boolean saveStudent(StudentDTO studentDTO, Connection connection);
    boolean deleteStudent(String id, Connection connection);
    boolean updateStudent(String id, StudentDTO studentDTO, Connection connection);
}
