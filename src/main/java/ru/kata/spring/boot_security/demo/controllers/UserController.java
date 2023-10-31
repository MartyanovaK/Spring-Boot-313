package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {
    private final UserDetailsService userService;

    public UserController(UserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUser(ModelMap model, Principal principal) {
        User user =(User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("helloMessage", "Hello, " + user.getUserName() + " " + user.getLastName() + "!");
        return "user";
    }
}
