package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SidePanelFormController {
    @FXML
    private AnchorPane childRootNode;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnPrograme;

    @FXML
    private JFXButton btnRegistration;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnUser;

    LoginFormController loginFormController = new LoginFormController();

    //String role = UserSession.getInstance().getRole();

    public void initialize() {
        try {
            btnDashboardOnAction();
            checkLoggedUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDate();
    }

    void checkLoggedUser() {
        if (loginFormController.getRole().equals("Coordinator")){
            btnUser.setVisible(false);
            btnPrograme.setVisible(false);
            btnPayment.setVisible(false);
            btnRegistration.setVisible(false);
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnDashboardOnAction() throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);


        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    @FXML
    void btnProgrameOnAction(ActionEvent event) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/program_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/student_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        AnchorPane dashRootNode = FXMLLoader.load(this.getClass().getResource("/view/user_set_form.fxml"));
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(dashRootNode);
    }

}
