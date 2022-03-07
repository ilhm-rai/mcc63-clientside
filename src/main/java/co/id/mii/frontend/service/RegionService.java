package co.id.mii.frontend.service;

import co.id.mii.frontend.model.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private List<Region> regions = new ArrayList<>();

    public RegionService() {
        regions.add(Region.builder().id(1L).name("Asia").build());
        regions.add(Region.builder().id(2L).name("Europa").build());
        regions.add(Region.builder().id(3L).name("America").build());
        regions.add(Region.builder().id(4L).name("Africa").build());
    }

    public List<Region> getAll() {
        return this.regions;
    }

    public Region getById(Long id) {
        return this.regions.stream()
                .filter(r -> r.getId() == id)
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
