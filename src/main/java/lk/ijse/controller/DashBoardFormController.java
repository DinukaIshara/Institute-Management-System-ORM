package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;

public class DashBoardFormController {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label lblregistrationcount;

    @FXML
    private Label lblstuentcount;

    @FXML
    private PieChart pieChart;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Registration);

    public void initialize() {
        setRegistrationCount();
        setStudentCount();
    }

    private void setRegistrationCount() {
        try {
            int registrationCount = registrationBO.registrationCount();
            lblregistrationcount.setText(String.valueOf(registrationCount));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load registration count").show();
        }
    }

    private void setStudentCount() {
        try {
            int studentCount = studentBO.StudentCount();
            lblstuentcount.setText(String.valueOf(studentCount));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load student count").show();
        }
    }
}
