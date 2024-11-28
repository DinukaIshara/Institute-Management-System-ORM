package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;
import lk.ijse.util.Regex;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;

public class UserSettingFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtReEnterPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    @FXML
    void btnChangePassOnAction(ActionEvent event) {
        UserDTO user = userBO.getUser(txtUserName.getText());
        String password =  txtPassword.getText();

        switch (isValied()){
            case 0: if (user != null) {
                String userName = user.getUsername();
                String dbPassword = user.getPassword();
                boolean isPassCorrect = PasswordUtil.verifyPassword(password, dbPassword);
                if (isPassCorrect) {
                    String newpassword  = txtReEnterPassword.getText();
                    String hashPass = PasswordUtil.hashPassword(newpassword);
                    boolean isPasswordUpdated = userBO.changePassword(userName,hashPass);
                    if (isPasswordUpdated) {
                        try {
                            navigatoT0AdminSidePanel();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        new Alert(Alert.AlertType.CONFIRMATION,"Password updated successfully").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Password not updated successfully").show();
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR,"Current Password not correct").show();
                }

            }else{
                new Alert(Alert.AlertType.ERROR,"User not found.").show();
            }
                break;

            case 1:
                new Alert(Alert.AlertType.ERROR,"Invalid user name").show();
                break;

            case 2:
                new Alert(Alert.AlertType.ERROR,"Invalid current password").show();
                break;

            case 3:
                new Alert(Alert.AlertType.ERROR,"Invalid new password").show();
                break;


        }
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
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtReEnterPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    public int isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtUserName)) return 1;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return 2;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtReEnterPassword)) return 3;

        return 0;
    }

}
