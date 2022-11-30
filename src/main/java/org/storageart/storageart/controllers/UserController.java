package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.repositories.UserRepository;
import org.storageart.storageart.service.UserService;

@Controller
public class UserController {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserService userService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("userData", new UserData());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("userData") UserData userData, BindingResult bindingResult) {
        if (userService.isUserExist(userData)) {
            bindingResult
                    .rejectValue("username", null, "User with such name is already exists");
            return "registration";
        }
        userService.addUser(userData);
        return "redirect:/login";
    }
}
