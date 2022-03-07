package co.id.mii.frontend.service;

import co.id.mii.frontend.model.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Region>>(){});
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Server unavailable");
        }
        
        return regions;
    }

    public Region getById(Long id) {
        return this.regions.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .collect(Collectors.toList())
                .get(0);
    }

    public void create(Region region) {
        Integer index = this.regions.size() - 1;

        Region regionEnd = this.regions.get(index);
        region.setId(regionEnd.getId() + 1);

        this.regions.add(region);
    }

    public void update(Region region, Long id) {
        Region oldRegion = getById(id);
        Integer index = regions.indexOf(oldRegion);

        this.regions.set(index, region);
    }

    public void delete(Long id) {
        Region region = getById(id);

        this.regions.remove(region);
    }
}
