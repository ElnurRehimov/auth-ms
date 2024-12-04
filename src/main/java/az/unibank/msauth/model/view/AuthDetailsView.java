package az.unibank.msauth.model.view;

import az.unibank.msauth.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AuthDetailsView {

    private Long userId;
    private String username;
    private Role role;
}
