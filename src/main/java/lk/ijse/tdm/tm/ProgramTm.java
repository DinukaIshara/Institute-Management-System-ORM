package lk.ijse.tdm.tm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProgramTm {
    @Id
    String pId;
    String pName;
    String duration;
    double fee;

}