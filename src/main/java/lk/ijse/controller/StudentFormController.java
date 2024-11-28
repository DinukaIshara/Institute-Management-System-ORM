package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.tm.StudentTm;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class StudentFormController {

    public ComboBox<String> cmbGender;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private ImageView imgProfilePic;

    private Image image;

    @FXML
    private ChoiceBox<String> scrollGender;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearchStudent;

    @FXML
    private TextField txtStudentId;

    ObservableList<StudentTm> obList;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);

    public void initialize(){
        getCurrentStudentId();
        setCellValueFactory();
        loadAllStudents();
        //setValueChexBox();

        ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(list);
        txtSearchStudent.setOnKeyPressed(this::handleEnterKey);
    }

    /*private void setValueChexBox() {
        scrollGender.setValue("Male");
        scrollGender.setValue("Female");
    }
*/
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("s_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("s_nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }



    private void loadAllStudents() {
        obList = FXCollections.observableArrayList();
        try {
            List<StudentDTO> studentList = studentBO.getAllCustomer();
            for (StudentDTO studentDTO : studentList) {
                StudentTm tm = new StudentTm(
                        studentDTO.getS_id(),
                        studentDTO.getS_name(),
                        studentDTO.getS_nic(),
                        studentDTO.getContact_no(),
                        studentDTO.getEmail(),
                        studentDTO.getAddress(),
                        studentDTO.getDob(),
                        studentDTO.getGender(),
                        studentDTO.getPath()
                );

                obList.add(tm);
            }

            tblStudent.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnAddPicOnAction(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", new String[]{"*png", "*jpg"}));

        File file = openFile.showOpenDialog(imgProfilePic.getScene().getWindow());
        if (file != null) {

            image = new Image(file.toURI().toString(), 120.0, 127.0, false, true);
            imgProfilePic.setImage(image);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String stuId = txtStudentId.getText();
        boolean isDeleted = studentBO.delete(stuId);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"customer deleted").show();
            loadAllStudents();
            clearFields();
            getCurrentStudentId();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String stuId = txtStudentId.getText();
        String stuName = txtName.getText();
        String stuNic = txtNIC.getText();
        String stuContact = txtContact.getText();
        String stuEmail = txtEmail.getText();
        String stuAddress = txtAddress.getText();
        LocalDate stuDob = txtDOB.getValue();
        String age = txtAge.getText();
        String stuGender = cmbGender.getValue();
        String path = image.getUrl();

        StudentDTO studentDTO = new StudentDTO(stuId,stuName,stuNic,stuContact,stuEmail,stuAddress,stuDob,age,stuGender,path);

        try {
            boolean isSaved = studentBO.saveStudent(studentDTO);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Student saved").show();
                clearFields();
                loadAllStudents();
            }
        } catch (Exception e) {
            new Alert( Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String stuId = txtStudentId.getText();
        String stuName = txtName.getText();
        String stuNic = txtNIC.getText();
        String stuContact = txtContact.getText();
        String stuEmail = txtEmail.getText();
        String stuAddress = txtAddress.getText();
        LocalDate stuDob = txtDOB.getValue();
        String age = txtAge.getText();
        String stuGender = cmbGender.getValue();
        String path = image.getUrl();

        StudentDTO studentDTO = new StudentDTO(stuId,stuName,stuNic,stuContact,stuEmail,stuAddress,stuDob,age,stuGender, path);

        try {
            boolean isUpdated = studentBO.updateStudent(studentDTO);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Student updated").show();
                clearFields();
                loadAllStudents();
            }
        } catch (Exception e) {
            new Alert( Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getCurrentStudentId() {
        try {
            String currentId = studentBO.getCurrentStudentId();
            String nextOrderId = generateNextOrderId(currentId);
            txtStudentId.setText(nextOrderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            /*String[] split = currentId.split("S-");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);*/
            int idNum = Integer.parseInt(currentId);
            return "S-" + ++idNum;
        }
        return "S-1";
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        String id = tblStudent.getSelectionModel().getSelectedItem().getS_id();

        try {
            StudentDTO studentDTO = studentBO.searchStudentId(id);
            if (studentDTO != null) {
                txtStudentId.setText(studentDTO.getS_id());
                txtName.setText(studentDTO.getS_name());
                txtNIC.setText(studentDTO.getS_nic());
                txtContact.setText(studentDTO.getContact_no());
                txtAddress.setText(studentDTO.getAddress());
                txtEmail.setText(studentDTO.getEmail());
                txtDOB.setValue(LocalDate.parse(studentDTO.getDob().toString()));
                txtAge.setText(studentDTO.getAge());
                cmbGender.setValue(studentDTO.getGender());
                Image image = new Image(studentDTO.getPath());
                imgProfilePic.setImage(image);


            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    void clearFields(){
        getCurrentStudentId();
        txtSearchStudent.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtNIC.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtDOB.setValue(null);
        txtAge.setText("");
        cmbGender.setValue("");
        imgProfilePic.setImage(null);
        txtName.requestFocus();
    }

    public void cmbGenderOnAction(ActionEvent actionEvent) {
        String s = cmbGender.getSelectionModel().getSelectedItem().toString();
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtSearchStudentOnAction(txtSearchStudent.getText());
        }
    }

    @FXML
    void txtSearchStudentOnAction(String id) {
        ObservableList<StudentTm> filteredList = obList.filtered(studentTm -> studentTm.getS_id().equals(id));
        System.out.println(filteredList);

        for (StudentTm item : filteredList) {
            txtStudentId.setText(item.getS_id());
            txtName.setText(item.getS_name());
            txtNIC.setText(item.getS_nic());
            Image image = new Image(item.getPath());
            imgProfilePic.setImage(image);
            txtEmail.setText(item.getEmail());
            txtContact.setText(item.getContact_no());
            txtAddress.setText(item.getAddress());
            txtDOB.setValue(item.getDob());
            LocalDate now = LocalDate.now();
            txtAge.setText(String.valueOf(Period.between(item.getDob(), now).getYears()));
            cmbGender.setValue(item.getGender());
        }

        tblStudent.setItems(filteredList);
    }

    @FXML
    void btnViewAllOnAction(ActionEvent event) {
        loadAllStudents();
        clearFields();
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtDOB.requestFocus();
    }

    @FXML
    void txtAgeOnAction(ActionEvent event) {
        cmbGender.requestFocus();
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtDOBOnAction(ActionEvent event) {
        txtAge.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtNIC.requestFocus();
    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }
}
