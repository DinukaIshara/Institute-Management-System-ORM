package lk.ijse.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {
    UserDTO getdata(String username);

    boolean saveUser(UserDTO userDTO);

    UserDTO getUser(String username);

    boolean deleteUser(String ids);

    boolean updateUser(UserDTO userDTO);

    ObservableList<UserDTO> getAllUsers();

    boolean changePassword(String userName, String password);

    boolean checkemail(String email);
}
