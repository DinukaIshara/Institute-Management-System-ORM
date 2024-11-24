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
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.util.Objects;

public class UsersFormController {
    @FXML
    private ComboBox<String> cmbAdminCoordinator;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRePassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnSignUp;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);


    public void initialize() {
        loadUser();
    }

    void loadUser() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            obList.add("Admin");
            obList.add("Coordinator");


            cmbAdminCoordinator.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSignUpOnAction() {
        String userType = cmbAdminCoordinator.getValue();
        String userNameText = txtUserName.getText();
        String emailText = txtEmail.getText();
        String passwordText = txtPassword.getText();
        String rePasswordText = txtRePassword.getText();

        String hasedPass = PasswordUtil.hashPassword(rePasswordText);

        //User user = userBO.searchUserbyName(userNameText);

        switch (isValied()) {
            case 0:
                if (passwordText.equals(rePasswordText)) {
                    //if (user != null) {
                        boolean isPassCorrect = PasswordUtil.verifyPassword(userNameText, rePasswordText);
                        if (isPassCorrect) {
                            User userDTO = new User(userNameText, hasedPass, userType, emailText);
                            boolean isSaved = userBO.saveUser(userDTO);
                            new Alert(Alert.AlertType.CONFIRMATION, "user  saved").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "user not saved.Enter valid admin code").show();
                        }

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Admin Name not found please enter valid admin name").show();
                    }

                //} else {
                    new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                //}
                break;

            case 1:
                new Alert(Alert.AlertType.ERROR, "Invalid UserName").show();
                break;

            case 2:
                new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
                break;

            case 3:
                new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
                break;

            case 4:
                new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
                break;

        }
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
    void txtPasswordOnAction(ActionEvent event) {
        btnSignUpOnAction();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        cmbAdminCoordinator.requestFocus();
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

    @FXML
    void txtRePassOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtRePassword);
    }

    public int isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtUserName)) return 1;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return 2;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return 3;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtRePassword)) return 4;
        return 0;
    }


}
