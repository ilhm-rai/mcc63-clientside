package co.id.mii.frontend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import co.id.mii.frontend.model.dto.LoginRequestData;
import co.id.mii.frontend.service.LoginService;

@Controller
public class AuthController {

    private LoginService loginService;

    @Autowired
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login(LoginRequestData loginRequestData) {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequestData loginRequestData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        System.out.println(loginService.login(loginRequestData));
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login?logout=true";
    }
}
