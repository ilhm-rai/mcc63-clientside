package co.id.mii.frontend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.id.mii.frontend.model.Region;
import co.id.mii.frontend.service.RegionService;

@Controller
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

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
    public String create(Region region) {
        return "region/create-form";
    }

    @PostMapping("/create")
    public String create(@Valid Region region, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "region/create-form";
        }

        redirect.addFlashAttribute("message", "Region successfully created...");
        regionService.create(region);
        return "redirect:/region";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("region", regionService.getById(id));
        return "region/update-form";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Region region,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "region/update-form";
        }

        redirect.addFlashAttribute("message", "Region successfully updated...");
        regionService.update(region, id);
        return "redirect:/region";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        regionService.delete(id);
        return "redirect:/region";
    }

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<List<Region>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }
}
