package co.id.mii.frontend.controller;

import java.util.List;

import javax.validation.Valid;

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

import co.id.mii.frontend.model.Employee;
import co.id.mii.frontend.model.dto.EmployeeDto;
import co.id.mii.frontend.model.dto.ResponseData;
import co.id.mii.frontend.service.EmployeeService;
import co.id.mii.frontend.service.RoleService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String index() {
        return "employee/index";
    }

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/detail";
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<Employee> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity add(@Valid @RequestBody EmployeeDto employeeDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        employeeService.create(employeeDto);
        return ResponseEntity.ok(new ResponseData("success", "Employee has been created"));
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDto employeeDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        employeeService.update(id, employeeDto);
        return ResponseEntity.ok(new ResponseData("success", "Employee has been updated"));
    }

    @DeleteMapping("/remove/{id}")
    public @ResponseBody ResponseEntity remove(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "Employee has been deleted"));
    }
}
