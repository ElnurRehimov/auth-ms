package az.unibank.msauth.model;

import az.unibank.msauth.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClaims {

    private Long userId;
    private String username;
    private Role role;
}