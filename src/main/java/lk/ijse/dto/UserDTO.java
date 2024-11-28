package lk.ijse.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;

}