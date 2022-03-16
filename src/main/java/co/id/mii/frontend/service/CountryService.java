/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import co.id.mii.frontend.model.Country;
import co.id.mii.frontend.model.dto.CountryDto;

/**
 *
 * @author RAI
 */
@Service
public class CountryService {

    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/country")
    private String url;

    @Autowired
    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Country> getAll() {
        List<Country> countries = new ArrayList<>();
        try {
            ResponseEntity<List<Country>> response = restTemplate
                    .exchange(url, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<Country>>() {
                            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return countries;
    }

    public Country getById(Long id) {
        Country country = new Country();
        try {
            ResponseEntity<Country> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Country>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }

        return country;
    }

    public void create(CountryDto countryDto) {
        try {
            restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(countryDto),
                    new ParameterizedTypeReference<CountryDto>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }

    public void update(Long id, CountryDto countryDto) {
        try {
            restTemplate.exchange(url.concat("/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(countryDto),
                    new ParameterizedTypeReference<CountryDto>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Country>() {
                    });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        }
    }
}
