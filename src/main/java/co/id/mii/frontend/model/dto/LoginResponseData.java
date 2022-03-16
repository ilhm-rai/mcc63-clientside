package co.id.mii.frontend.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponseData {
    private String username;
    private String email;
    private String address;
    private List<String> authorities;
}
