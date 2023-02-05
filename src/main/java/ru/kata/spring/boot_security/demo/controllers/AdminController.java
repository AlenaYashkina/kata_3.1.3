package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String getCreatePage(@ModelAttribute("user") User user,
                                Model model) {

        Collection<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam("roles") Collection<Role> roles) {

        if (bindingResult.hasErrors()) {
            return "new";
        }

        user.setRoles(roles);
        userService.createOrUpdateUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(Model model,
                          @PathVariable("id") long id) {

        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model,
                              @PathVariable("id") long id) {

        Collection<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());

        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable long id,
                             @RequestParam("roles") Collection<Role> roles) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        user.setRoles(roles);
        userService.createOrUpdateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {

        userService.deleteUser(userService.getUser(id));
        return "redirect:/admin";
    }
}
