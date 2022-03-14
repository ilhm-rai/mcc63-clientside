package co.id.mii.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import co.id.mii.frontend.model.Role;

@Service
public class RoleService {

    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/role")
    private String url;

    @Autowired
    public RoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Role> getAll() {
        List<Role> countries = new ArrayList<>();
        try {
            ResponseEntity<List<Role>> response = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Role>>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return countries;
    }

    public Role getById(Long id) {
        Role Role = new Role();
        try {
            ResponseEntity<Role> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Role>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return Role;
    }

    public void create(Role role) {
        try {
            restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(role),
                    new ParameterizedTypeReference<Role>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }

    public void update(Long id, Role role) {
        try {
            restTemplate.exchange(url.concat("/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(role),
                    new ParameterizedTypeReference<Role>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Role>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }
}
