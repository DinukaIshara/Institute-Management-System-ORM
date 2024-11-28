package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

public class LoginFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private TextField txtPasswordVisible;

    static String roleAll = "";

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);


    @FXML
    private void togglePasswordVisibility() {
        if (txtPasswordVisible.isVisible()) {
            // Hide the visible TextField and show the PasswordField
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);

            // Copy text back to the PasswordField
            txtPassword.setText(txtPasswordVisible.getText());
        } else {
            // Show the visible TextField and hide the PasswordField
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);

            // Copy text to the visible TextField
            txtPasswordVisible.setText(txtPassword.getText());
        }
    }



    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();


        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
        } else {
            UserDTO userDTO = userBO.getdata(username);
            if (userDTO == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            } else {
                if (BCrypt.checkpw(password, userDTO.getPassword())) {
                    String role = userDTO.getRole();

                    if (role != null && role.equals("Admin")) {
                        roleAll = role;
                        navigatoT0AdminSidePanel();
                        new Alert(Alert.AlertType.CONFIRMATION,"welcome Admin "+username).show();

                    } else if (role != null && role.equals("Coordinator")) {
                        roleAll = role;
                        navigatoT0AdminSidePanel();
                        new Alert(Alert.AlertType.CONFIRMATION,"welcome coordinator "+username).show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid role").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid password").show();
                }
            }
        }
    }

    public String getRole(){
        return roleAll;
    }

    void navigatoT0AdminSidePanel() throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/sidePanel_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Home Page");
    }

    @FXML
    void hyperSignUpOnAction(ActionEvent event) throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/user_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Sign Up");
    }

    @FXML
    void hypoForgetPassOnAction(ActionEvent event) throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/reset_password_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Setting");
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLogin.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

}
