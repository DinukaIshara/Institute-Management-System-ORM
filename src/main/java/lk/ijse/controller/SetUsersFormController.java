package lk.ijse.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.tdm.tm.StudentTm;
import lk.ijse.util.Regex;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Period;

public class SetUsersFormController {
    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colrole;

    @FXML
    private TextField emailtext;

    @FXML
    private TextField nametext;

    @FXML
    private TextField passwordtext;

    @FXML
    private TextField roletext;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<UserDTO> tbluser;

    @FXML
    private TextField txtSearch;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);



    public void initialize() {
        getallusers();
        setcellvaluefactory();
    }

    private void getallusers() {
        ObservableList<UserDTO> studentDTOS=userBO.getAllUsers();
        tbluser.setItems(studentDTOS);
    }

    private void setcellvaluefactory() {
        colname.setCellValueFactory(new PropertyValueFactory<>("username"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        String name = txtSearch.getText();

        boolean result = userBO.deleteUser(name);
        if (result) {
            clearfields();
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION, "delete user successful").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "delete user failed").show();
        }
    }

    @FXML
    void emailOnAction(KeyEvent event) {

    }

    @FXML
    void nameonAction(KeyEvent event) {

    }

    @FXML
    void passwordonAction(KeyEvent event) {

    }

    @FXML
    void roleonAction(KeyEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String name = nametext.getText();
        String password = passwordtext.getText();
        String email = emailtext.getText();
        String role = roletext.getText();
        String encript= BCrypt.hashpw(password,BCrypt.gensalt());
        UserDTO userDTO = new UserDTO( name, email,encript, role);
        if (isValied()){
            boolean result = userBO.saveUser(userDTO);
            if (result) {
                clearfields();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Registration user successful").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Registration user failed").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "wrong inputs try again").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearfields();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String name = nametext.getText();
        String password = passwordtext.getText();
        String email = emailtext.getText();
        String role = roletext.getText();
        String encript= BCrypt.hashpw(password,BCrypt.gensalt());
        UserDTO userDTO = new UserDTO( name, email,encript, role);
        if (isValied()){
            boolean result = userBO.updateUser(userDTO);
            if (result) {
                clearfields();
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Update user successful").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update user failed").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "wrong inputs try again").show();
        }
    }

    private void clearfields() {
        nametext.clear();
        passwordtext.clear();
        emailtext.clear();
        roletext.clear();
    }

    ObservableList<UserDTO> obList;

    @FXML
    void searchOnAction(ActionEvent event) {
        String name = txtSearch.getText();
        UserDTO userDTO = userBO.getUser(name);

        if (userDTO != null) {
            nametext.setText(name);
            emailtext.setText(userDTO.getEmail());
            passwordtext.setText(userDTO.getPassword());
            roletext.setText(userDTO.getRole());
        }
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,nametext)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,emailtext)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,passwordtext)) return false;
        //if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtRePassword)) return 4;
        return true;
    }
}
