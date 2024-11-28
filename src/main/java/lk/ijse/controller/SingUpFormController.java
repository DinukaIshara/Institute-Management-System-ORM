package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.util.Regex;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;

public class SingUpFormController {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<String> cmbSelectRole;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    public void initialize() {
        loadUser();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String role = cmbSelectRole.getValue();
        String encript= BCrypt.hashpw(password,BCrypt.gensalt());

        UserDTO userDTO = new UserDTO( name, email,encript, role);

        if (isValied()){
            boolean result = userBO.saveUser(userDTO);
            if (result) {
                //getallusers();
                //clearfields();
                new Alert(Alert.AlertType.CONFIRMATION, "Registration user successful").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Registration user failed").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "wrong inputs try again").show();
        }
    }

    void loadUser() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            obList.add("Admin");
            obList.add("Coordinator");


            cmbSelectRole.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbSelectRoleOnAction(ActionEvent event) {

    }

    @FXML
    void hypoSignInOnAction(ActionEvent event) throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Log In");
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME,txtUserName);
    }

    @FXML
    void txtPassOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return false;
        //if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtRePassword)) return 4;
        return true;
    }
}
