package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegistrationDTO {
    private String sid;
    private String pid;
    private LocalDate date;
    private double upfrontpayment;
    private double amoutPaybale;


}
