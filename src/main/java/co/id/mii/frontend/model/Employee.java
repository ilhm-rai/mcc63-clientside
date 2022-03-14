package co.id.mii.frontend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private Long id;

    @NotNull(message = "Fullname is required")
    private String fullName;

    private String address;

    @NotNull(message = "Email is required")
    @Email(message = "Input valid email")
    private String email;

    private User user;
}
