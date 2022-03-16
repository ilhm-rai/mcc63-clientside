package co.id.mii.frontend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import co.id.mii.frontend.model.dto.LoginRequestData;
import co.id.mii.frontend.model.dto.LoginResponseData;

@Service
public class LoginService {

    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/login")
    private String url;

    @Autowired
    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean login(LoginRequestData loginRequestData) {
        try {
            ResponseEntity<LoginResponseData> res = restTemplate
                    .exchange(url, HttpMethod.POST, new HttpEntity<>(loginRequestData),
                            new ParameterizedTypeReference<LoginResponseData>() {
                            });

            if (res.getStatusCode() == HttpStatus.OK) {
                setAuthentication(res.getBody(), loginRequestData.getPassword());
                return true;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }

        return false;
    }

    public void setAuthentication(LoginResponseData res, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(res.getUsername(),
                password, getAuthorities(res.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public Collection<GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toList());
    }
}