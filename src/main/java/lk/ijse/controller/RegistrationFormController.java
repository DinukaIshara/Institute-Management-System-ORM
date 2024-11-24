package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.tm.CartTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegistrationFormController {

    @FXML
    private ComboBox<String> cmbProgramNames;

    @FXML
    private TableColumn<?, ?> colAmountPaybale;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private ImageView imgProfilePic;

    @FXML
    private AnchorPane imgRootNode;

    @FXML
    private Label lblProgrameFee;

    @FXML
    private TextField lblRegisterId;

    @FXML
    private TableView<CartTm> tblRegistration;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtUpfrontPayment;

    @FXML
    private AnchorPane childRootNode;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);

    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Programs);

    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Registration);

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        getProgramNames();
        setCellValueFactory();
        getDateOnAction();
        getCurrentRegistrationId();
    }

    private void getCurrentRegistrationId() {
        try {
            int currentId = registrationBO.getCurrentRegistrationId();
            String nextOrderId = generateNextOrderId(currentId);
            lblRegisterId.setText(nextOrderId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private String generateNextOrderId(int currentId) {
        if(currentId != 0) {
            int idNum = currentId;
            System.out.println(idNum);
            return "R-" + ++idNum;
        }
        return "R-1";
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colUpfrontPayment.setCellValueFactory(new PropertyValueFactory<>("upfrontpayment"));
        colAmountPaybale.setCellValueFactory(new PropertyValueFactory<>("paybale"));

    }

    private void getProgramNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ProgramDTO> programList = programBO.getAllPrograms();
            List<String> programNameList = new ArrayList<>();

            for (ProgramDTO programDTO: programList){
                programNameList.add(programDTO.getPName());
            }
            for (String name: programNameList) {
                obList.add(name);
            }
            cmbProgramNames.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbProgramNamesOnAction(ActionEvent event) {
        String name = cmbProgramNames.getValue();
        try {
            ProgramDTO program = programBO.searchProgramByName(name);
            if (program != null) {
                txtProgramId.setText(program.getPId());
                lblProgrameFee.setText(String.valueOf(program.getFee()));
                txtUpfrontPayment.requestFocus();
            }

            txtUpfrontPayment.requestFocus();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String pId = txtProgramId.getText();
        String pName = cmbProgramNames.getValue();
        double fee = Double.parseDouble(lblProgrameFee.getText());
        double upfrontPay = Double.parseDouble(txtUpfrontPayment.getText());
        double paybale = fee - upfrontPay;


        CartTm tm = new CartTm(pId, pName, fee, upfrontPay,paybale);
        obList.add(tm);
        tblRegistration.setItems(obList);
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        List<RegistrationDTO> registrationDTOList = new ArrayList<>();

        for (CartTm cartTm : tblRegistration.getItems()) {
            RegistrationDTO registrationDTO = new RegistrationDTO(
                    //String.valueOf((Integer.parseInt(registrationBO.getCurrentRegistrationId())+1)),           // Registration ID
                    txtStudentId.getText(),            // Student ID
                    cartTm.getId(),                     // Program ID
                    txtDate.getValue(),
                    cartTm.getUpfrontpayment(),        // Upfront Payment
                    cartTm.getPaybale()// Date (formatted as String)
            );

            // Add each RegistrationDTO to the list
            registrationDTOList.add(registrationDTO);
        }

        registrationBO.placeRegister(registrationDTOList);

    }

    @FXML
    void getDateOnAction() {
        LocalDate now = LocalDate.now();
        txtDate.setValue(now);
    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {
        String id = txtStudentId.getText();
        try {
            StudentDTO student = studentBO.searchStudentId(id);
            if (student != null) {
                txtStudentName.setText(student.getS_name());
                Image image = new Image(student.getPath());
                imgProfilePic.setImage(image);


            }else{
                cmbProgramNames.requestFocus();
                new Alert(Alert.AlertType.INFORMATION, "student not found!").show();
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtStudentIdOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void btnViewRegisterOnAction(ActionEvent event) {
        AnchorPane inNode = null;
        try {
            inNode = FXMLLoader.load(this.getClass().getResource("/view/view_registration_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRootNode.getChildren().clear();
        childRootNode.getChildren().add(inNode);
    }


}
