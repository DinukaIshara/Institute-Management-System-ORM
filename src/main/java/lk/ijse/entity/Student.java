package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Student {
    @Id
    private String s_id;
    private String s_name;
    private String s_nic;
    private String contact_no;
    private String email;
    private String address;
    private LocalDate dob;
    private String age;
    private String gender;
    private String Path;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Registration> registrationList;

}
