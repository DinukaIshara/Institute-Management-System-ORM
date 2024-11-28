package lk.ijse.dto;

import jakarta.persistence.ManyToOne;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;

import java.time.LocalDate;

public class PaymentDTO {
    private double upfrontpayment;
    private double balance;
    private LocalDate date;
    private Student student;
    private Program program;
}
