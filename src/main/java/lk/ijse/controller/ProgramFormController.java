package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.tdm.tm.ProgramTm;

import java.sql.SQLException;
import java.util.List;

public class ProgramFormController {

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblProgramId;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProgramName;

    @FXML
    private TextField txtProgramId;

    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Programs);

    public void initialize(){
        getCurrentProgramId();
        setCellValueFactory();
        loadAllPrograms();
        txtPIdOnAction();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

    }

    private void loadAllPrograms() {
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();
        try {
            List<ProgramDTO> programList = programBO.getAllPrograms();
            for (ProgramDTO programDTO : programList) {
                ProgramTm tm = new ProgramTm(
                        programDTO.getPId(),
                        programDTO.getPName(),
                        programDTO.getDuration(),
                        programDTO.getFee()
                );

                obList.add(tm);
            }

            tblProgram.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String proId = txtProgramId.getText();
        boolean isDeleted = programBO.delete(proId);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"program deleted").show();
            loadAllPrograms();
            clearFields();
            getCurrentProgramId();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String proId = txtProgramId.getText();
        String proName = txtProgramName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());


        ProgramDTO programDTO = new ProgramDTO(proId,proName,duration,fee);

        try {
            boolean isSaved = programBO.saveProgram(programDTO);
            if (isSaved ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Program saved").show();
                clearFields();
                loadAllPrograms();
            }
        } catch (Exception e) {
            new Alert( Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String proId = txtProgramId.getText();
        String proName = txtProgramName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());


        ProgramDTO programDTO = new ProgramDTO(proId,proName,duration,fee);

        try {
            boolean isUpdated = programBO.updateProgram(programDTO);
            if (isUpdated ) {
                new Alert(Alert.AlertType.CONFIRMATION,"Program updated").show();
                clearFields();
                loadAllPrograms();
            }
        } catch (Exception e) {
            new Alert( Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void getCurrentProgramId() {
        try {
            String currentId = programBO.getCurrentProgramId();
            String nextOrderId = generateNextOrderId(currentId);
            txtProgramId.setText(nextOrderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            int idNum = Integer.parseInt(currentId);
            return "CA" + ++idNum;
        }
        return "CA1001";
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        String id = tblProgram.getSelectionModel().getSelectedItem().getPId();

        try {
            ProgramDTO programDTO = programBO.searchProgramId(id);//searchProductId(id);
            if (programDTO != null) {
                txtProgramId.setText(programDTO.getPId());
                txtProgramName.setText(programDTO.getPName());
                txtDuration.setText(programDTO.getDuration());
                txtFee.setText(String.valueOf(programDTO.getFee()));


            } else {
                new Alert(Alert.AlertType.INFORMATION, "program not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    void clearFields(){
        getCurrentProgramId();
        txtPIdOnAction();
        txtProgramName.setText("");
        txtDuration.setText("");
        txtFee.setText("");
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @FXML
    void txtDurationOnAction(ActionEvent event) {
        txtFee.requestFocus();
    }

    @FXML
    void txtPIdOnAction() {
        txtProgramName.requestFocus();
    }

    @FXML
    void txtPNameOnAction(ActionEvent event) {
        txtDuration.requestFocus();
    }

}
