package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @ManyToOne
    private Program program;

    private LocalDate date;
    private double upfrontpayment;
    private double amoutPaybale;

    public Registration(Student student, Program program, LocalDate date, double upfrontpayment, double amoutPaybale) {
        this.student = student;
        this.program = program;
        this.date = date;
        this.upfrontpayment = upfrontpayment;
        this.amoutPaybale = amoutPaybale;
    }
}
