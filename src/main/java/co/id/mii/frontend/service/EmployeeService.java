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

import co.id.mii.frontend.model.Employee;
import co.id.mii.frontend.model.dto.EmployeeDto;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/employee")
    private String url;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            ResponseEntity<List<Employee>> response = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return employees;
    }

    public Employee getById(Long id) {
        Employee employee = new Employee();
        try {
            ResponseEntity<Employee> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Employee>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return employee;
    }

    public void create(EmployeeDto employeeDto) {
        try {
            restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(employeeDto),
                    new ParameterizedTypeReference<Employee>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }

    public void update(Long id, EmployeeDto employeeDto) {
        try {
            restTemplate.exchange(url.concat("/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(employeeDto),
                    new ParameterizedTypeReference<Employee>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<String>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }
}
