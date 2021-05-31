package com.ivankharytanovich.springbootapp.controller;

import com.ivankharytanovich.springbootapp.model.User;
import com.ivankharytanovich.springbootapp.model.enums.Status;
import com.ivankharytanovich.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String mainPageController(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users-page";
    }

    @GetMapping("/user/new")
    public String createNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-create";
    }

    @PostMapping("/user/new")
    public String savingNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("usernameTaken", "username " + user.getUsername() + " already exists");
            return "user-create";
        } else if (bindingResult.hasErrors()) {
            return "user-create";
        } else {
            String pwd = user.getPassword();
            String encryptPwd = passwordEncoder.encode(pwd);
            user.setPassword(encryptPwd);

            userService.saveUser(user);
            return "redirect:/user";
        }
    }

    @GetMapping("/user/{id}")
    public String getUserDetails(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-details";
    }

    @GetMapping("/user/{id}/edit")
    public String editUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String saveEditedUserById(@PathVariable("id") Long id, @Valid User user, BindingResult bindingResult, Model model) {
        if (userService.isUsernameTaken(user.getUsername())
                && userService.getIdByUsername(user.getUsername()) != id.longValue()) {
            model.addAttribute("usernameTaken", "username " + user.getUsername() + " already exists");
            return "user-edit";
        } else if (bindingResult.hasErrors()) {
            return "user-edit";
        } else {
            String pwd = user.getPassword();
            String encryptPwd = passwordEncoder.encode(pwd);
            user.setPassword(encryptPwd);

            userService.updateUserById(id, user);
            return "redirect:/user";
        }
    }
}
