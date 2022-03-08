package co.id.mii.frontend.service;

import co.id.mii.frontend.model.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegionService {

    private List<Region> regions = new ArrayList<>();
    private RestTemplate restTemplate;

    @Value("${app.baseUrl}/region")
    private String url;

    @Autowired
    public RegionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Region> getAll() {
        try {
            ResponseEntity<List<Region>> response = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Region>>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }

        return regions;
    }

    public Region getById(Long id) {
//        return regions.stream()
//                .filter(r -> Objects.equals(r.getId(), id))
//                .collect(Collectors.toList())
//                .get(0);
        Region region = new Region();
        try {
            ResponseEntity<Region> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null, new ParameterizedTypeReference<Region>() {
            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return region;
    }

    public void create(Region region) {
//        Integer index = this.regions.size() - 1;
//        Region regionEnd = this.regions.get(index);
//        region.setId(regionEnd.getId() + 1);
//        regions.add(region);
        try {
            ResponseEntity<Region> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(region),
                    new ParameterizedTypeReference<Region>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }
    }

    public void update(Region region, Long id) {
//        Region oldRegion = getById(id);
//        Integer index = regions.indexOf(oldRegion);
//        regions.set(index, region);
        try {
            region.setId(id);
            ResponseEntity<Region> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.PUT, new HttpEntity<>(region),
                    new ParameterizedTypeReference<Region>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }
    }

    public void delete(Long id) {
//        Region region = getById(id);
//        regions.remove(region);
        try {
            ResponseEntity<Region> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<Region>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }
    }
}
