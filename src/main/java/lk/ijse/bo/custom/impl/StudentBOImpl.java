package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Student);

    public boolean saveStudent(StudentDTO studentDTO) {
        return studentDAO.save( new Student(studentDTO.getS_id(),studentDTO.getS_name(),studentDTO.getS_nic(),studentDTO.getContact_no(),studentDTO.getEmail(), studentDTO.getAddress(),studentDTO.getDob(),studentDTO.getAge(),studentDTO.getGender(), studentDTO.getPath(),new ArrayList<Registration>()));
    }

    public String getCurrentStudentId() throws SQLException {
        return studentDAO.getCurrentId();
    }

    public boolean updateStudent(StudentDTO studentDTO) {
        return studentDAO.update(new Student(studentDTO.getS_id(),studentDTO.getS_name(),studentDTO.getS_nic(),studentDTO.getContact_no(),studentDTO.getEmail(), studentDTO.getAddress(),studentDTO.getDob(),studentDTO.getAge(),studentDTO.getGender(), studentDTO.getPath(),new ArrayList<Registration>()));
    }

    @Override
    public StudentDTO searchStudentId(String id) {
        Student student = studentDAO.searchId(id);
        return new StudentDTO(student.getS_id(),student.getS_name(),student.getS_nic(),student.getContact_no(),student.getEmail(), student.getAddress(),student.getDob(),student.getAge(),student.getGender(), student.getPath());
    }

    @Override
    public List<StudentDTO> getAllCustomer() throws SQLException,ClassNotFoundException{

        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> students = studentDAO.getAll();

        for (Student student:students) {
            StudentDTO studentDTO = new StudentDTO(student.getS_id(),student.getS_name(),student.getS_nic(),student.getContact_no(),student.getEmail(), student.getAddress(),student.getDob(), student.getAge(),student.getGender(), student.getPath());
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    public boolean delete(String id){
        return studentDAO.delete(id);
    }


}
