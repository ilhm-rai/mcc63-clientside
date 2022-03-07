package co.id.mii.frontend.controller;

import co.id.mii.frontend.model.Region;
import co.id.mii.frontend.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/region")
public class RegionController {

    private RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("regions", regionService.getAll());

        return "region/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("region", regionService.getById(id));
        return "region/getById";
    }

    @GetMapping("/create")
    public String create() {
        return "region/create-form";
    }

    @PostMapping("/create")
    public String create(Region region) {
        regionService.create(region);
        return "redirect:/region";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("region", regionService.getById(id));
        return "region/update-form";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Region region){
        regionService.update(region, id);
        return "redirect:/region";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        regionService.delete(id);
        return "redirect:/region";
    }
}
