package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.services.ArtService;
import org.storageart.storageart.services.UserService;

@Controller
public class UserController {

    private UserService userService;

    private ArtService artService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setArtService(ArtService artService) {
        this.artService = artService;
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @GetMapping("")
    public String homeGet() {
        return "home";
    }

    @GetMapping("/hello")
    public String helloGet(Model model) {

        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("artsData", artService.getByUser(userData));

        return "hello";
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
        userService.saveUser(userData);
        return "redirect:/login";
    }
}
