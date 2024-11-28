package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    boolean ifHaveAdmins();
    boolean saveUser(User user);

    User getdatas(String ids);


    boolean deleteUser(String ids);

    List<User> getAllUsers();

    boolean changePassword(String email, String password);


    boolean checkemail(String email);
}
