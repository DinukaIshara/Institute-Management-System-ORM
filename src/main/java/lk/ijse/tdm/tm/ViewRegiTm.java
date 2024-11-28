package lk.ijse.tdm.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Data
@NoArgsConstructor
public class ViewRegiTm {
    private String rid;
    private String pid;
    private String courseName;
    private String fee;
    private String amount;
    private String amountPay;
    private String sid;
    private String s_name;
    private String s_nic;
    private String contact_no;
    private String email;
    private String Path;
}
