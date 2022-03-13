package co.id.mii.frontend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.id.mii.frontend.model.Region;
import co.id.mii.frontend.model.dto.ResponseData;
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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("region", regionService.getById(id));
        return "region/update-form";
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @Valid Region region,
            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "region/update-form";
        }

        redirect.addFlashAttribute("message", "Region successfully updated...");
        regionService.update(id, region);
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

    @PostMapping("/add")
    public @ResponseBody ResponseEntity add(@Valid @RequestBody Region region, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        regionService.create(region);
        return ResponseEntity.ok(new ResponseData("success", "region has been created"));
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody Region region,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        regionService.update(id, region);
        return ResponseEntity.ok(new ResponseData("success", "region has been updated"));
    }

    @DeleteMapping("/remove/{id}")
    public @ResponseBody ResponseEntity remove(@PathVariable("id") Long id) {
        regionService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "region has been deleted"));
    }
}
