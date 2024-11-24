package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewRegistrationFormController {

    @FXML
    private TableColumn<?, ?> colAmountPaybale;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colProgram_Id;

    @FXML
    private TableColumn<?, ?> colPrograme_Name;

    @FXML
    private TableColumn<?, ?> colR_Id;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private ImageView imgProfilePic;

    @FXML
    private AnchorPane imgRootNode;

    @FXML
    private TableView<?> tblViewRegistration;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    private AnchorPane childRootNode;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane inNode = null;
        try {
            inNode = FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(inNode);
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtNicOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane inNode = null;
        try {
            inNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(inNode);
    }
}
