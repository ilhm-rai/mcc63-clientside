package co.id.mii.frontend.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestData {

    @NotEmpty(message = "Username or Email are required")
    private String usernameOrEmail;

    @NotEmpty(message = "Password is required")
    private String password;
}
