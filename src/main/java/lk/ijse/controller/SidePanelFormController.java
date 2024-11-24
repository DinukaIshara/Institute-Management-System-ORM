package lk.ijse.controller;

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

    public void initialize() {
        try {
            btnDashboardOnAction();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDate();
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

}
