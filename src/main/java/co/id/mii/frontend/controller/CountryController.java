/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.frontend.controller;

import co.id.mii.frontend.model.dto.CountryDto;
import co.id.mii.frontend.service.CountryService;
import co.id.mii.frontend.service.RegionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author RAI
 */
@Controller
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;
    private final RegionService regionService;

    @Autowired
    public CountryController(CountryService countryService, RegionService regionService) {
        this.countryService = countryService;
        this.regionService = regionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("countries", countryService.getAll());
        return "country/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("country", countryService.getById(id));
        return "country/detail";
    }

    @GetMapping("/create")
    public String create(CountryDto countryDto, Model model) {
        model.addAttribute("regions", regionService.getAll());
        return "country/create";
    }

    @PostMapping("/create")
    public String save(@Valid CountryDto countryDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionService.getAll());
            return "country/create";
        }

        countryService.create(countryDto);
        return "redirect:/country";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, CountryDto countryDto, Model model) {
        model.addAttribute("regions", regionService.getAll());
        model.addAttribute("country", countryService.getById(id));
        return "country/edit";
    }
    
    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @Valid CountryDto countryDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionService.getAll());
            return "country/create";
        }

        countryService.create(countryDto);
        return "redirect:/country";
    }
    
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        countryService.delete(id);
        return "redirect:/country";
    }
}
