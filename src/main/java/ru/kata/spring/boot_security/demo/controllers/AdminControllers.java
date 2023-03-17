package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminControllers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        Long id = userService.findByUsername(principal.getName()).getId();
        model.addAttribute("users", userService.allUsers());
        User user = new User();
        model.addAttribute("user", user);
        User userInfo = userService.findUserById(id);
        model.addAttribute("infoTop", userInfo);
        List<Role> setRoles = roleService.getRoles();
        model.addAttribute("list", setRoles);
        return "listUsers";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userId", userService.findUserById(id));
        return "userById";
    }

    @GetMapping("/create")
    public String createUserFrom(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> setRoles = roleService.getRoles();
        model.addAttribute("list", setRoles);
        return "create";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        List<Role> roles = roleService.getRoles();
        model.addAttribute("rolesAdd", roles);
        System.err.println("point one: " + userService.findUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        System.err.println("point three: " + user);
        userService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
