package lk.ijse.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Users);
    @Override
    public UserDTO getdata(String username) {
        User user = userDAO.getdatas(username);
        if (user == null) {
            return null;
        } else {
            return new UserDTO(user.getUsername(),user.getEmail(),user.getPassword(),user.getRole());
        }
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.saveUser(new User(userDTO.getUsername(),userDTO.getEmail(),userDTO.getPassword(),userDTO.getRole()));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return userDAO.update(new User(userDTO.getUsername(),userDTO.getEmail(),userDTO.getPassword(),userDTO.getRole()));
    }

    @Override
    public UserDTO getUser(String ids) {

        User user = userDAO.getdatas(ids);
        if (user == null) {
            return null;
        } else {
            return new UserDTO(user.getUsername(),user.getEmail(),user.getPassword(),user.getRole());
        }
    }



    @Override
    public boolean deleteUser(String ids) {
        return userDAO.deleteUser(ids);
    }

    @Override
    public ObservableList<UserDTO> getAllUsers() {
        List<User> users=userDAO.getAllUsers();
        List<UserDTO> userDTOS=new ArrayList<>();
        for(User user:users){
            userDTOS.add(new UserDTO(user.getUsername(),user.getEmail(),user.getPassword(),user.getRole()));
        }
        return FXCollections.observableArrayList(userDTOS);
    }

    @Override
    public boolean changePassword(String email, String password) {

        return userDAO.changePassword(email,password);
    }

    @Override
    public boolean checkemail(String email) {

        return userDAO.checkemail(email);
    }


}
