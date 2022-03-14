package co.id.mii.frontend.model.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.id.mii.frontend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    @NotNull(message = "Fullname is required")
    private String fullName;

    private String address;

    @NotNull(message = "Email is required")
    @Email(message = "Input valid email")
    private String email;

    private User user;

    @NotEmpty(message = "Role is required, choose at least one role")
    private List<Long> roleIds;
}
