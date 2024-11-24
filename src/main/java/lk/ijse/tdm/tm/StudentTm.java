package lk.ijse.tdm.tm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentTm {
    @Id
    private String s_id;
    private String s_name;
    private String s_nic;
    private String contact_no;
    private String email;
    private String address;
    private LocalDate dob;
    private String gender;
    private String Path;
}
