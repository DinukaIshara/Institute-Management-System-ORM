package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;
import lk.ijse.tdm.tm.ViewRegiTm;

import java.io.IOException;
import java.util.List;

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
    private TableColumn<?, ?> colSId;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private ImageView imgProfilePic;

    @FXML
    private AnchorPane imgRootNode;

    @FXML
    private TableView<ViewRegiTm> tblViewRegistration;

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

    ObservableList<ViewRegiTm> obList;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);

    public void initialize(){
        loadRegiDetails();
        setCellValueFactory();
        loadAllRegiDetails();
        txtStudentId.setOnKeyPressed(this::handleEnterKey);
        clear();
    }

    public void setCellValueFactory(){
        colR_Id.setCellValueFactory(new PropertyValueFactory<>("rid"));
        colProgram_Id.setCellValueFactory(new PropertyValueFactory<>("pid"));
        colPrograme_Name.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colUpfrontPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAmountPaybale.setCellValueFactory(new PropertyValueFactory<>("amountPay"));
        colSId.setCellValueFactory(new PropertyValueFactory<>("sid"));

    }

    public void loadAllRegiDetails(){
        List<Student> students = studentBO.getStudentWithEnrolledPrograms();
        obList = FXCollections.observableArrayList();
        for (Student student : students) {
            System.out.println("Student: " + student.getS_name());
            for (Registration registration : student.getRegistrationList()) {
                System.out.println("Enrolled in Program: " + registration.getProgram().getPName());
                ViewRegiTm viewRegiTm = new ViewRegiTm("R-"+registration.getRid(),registration.getProgram().getPId(),registration.getProgram().getPName(), String.valueOf(registration.getProgram().getFee()),String.valueOf(registration.getUpfrontpayment()),String.valueOf(registration.getAmoutPaybale()),student.getS_id(), student.getS_name(), student.getS_nic(), student.getContact_no(), student.getEmail(), student.getPath());
                obList.add(viewRegiTm);
            }
            txtStudentName.setText(student.getS_name());
            txtNIC.setText(student.getS_nic());
            Image image = new Image(student.getPath());
            imgProfilePic.setImage(image);
            txtEmail.setText(student.getEmail());
            txtContact.setText(student.getContact_no());
        }

        tblViewRegistration.setItems(obList);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchStudentById(txtStudentId.getText());
        }
    }

    private void searchStudentById(String id) {
        // Filter the obList by the studentId
        ObservableList<ViewRegiTm> filteredList = obList.filtered(registration -> registration.getSid().equals(id));

        for (ViewRegiTm item : filteredList) {
            txtStudentName.setText(item.getS_name());
            txtNIC.setText(item.getS_nic());
            Image image = new Image(item.getPath());
            imgProfilePic.setImage(image);
            txtEmail.setText(item.getEmail());
            txtContact.setText(item.getContact_no());
        }


        // Set the filtered list to the table view
        tblViewRegistration.setItems(filteredList);
    }

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

    void loadRegiDetails(){
        List<Student> students = studentBO.getStudentWithEnrolledPrograms();
        for (Student student : students) {
            System.out.println("Student: " + student.getS_name());
            for (Registration registration : student.getRegistrationList()) {
                System.out.println("Enrolled in Program: " + registration.getProgram().getPName());
            }
        }

    }

    @FXML
    void btnViewAllOnAction(ActionEvent event) {
        loadAllRegiDetails();
        clear();
    }

    void clear(){
        txtStudentId.requestFocus();
        txtStudentId.setText("");
        txtStudentName.setText("");
        txtNIC.setText("");
        imgProfilePic.setImage(null);
        txtEmail.setText("");
        txtContact.setText("");
    }

}
