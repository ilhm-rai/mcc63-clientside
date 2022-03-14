package co.id.mii.frontend.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    private List<User> users;

    private List<Privilege> privileges;
}