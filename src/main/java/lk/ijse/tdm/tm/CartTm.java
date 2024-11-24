package lk.ijse.tdm.tm;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CartTm {
    String Id;
    String name;
    double Fee;
    double upfrontpayment;
    double paybale;

}
