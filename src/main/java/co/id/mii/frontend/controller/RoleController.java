package co.id.mii.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.id.mii.frontend.model.Role;
import co.id.mii.frontend.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }
}