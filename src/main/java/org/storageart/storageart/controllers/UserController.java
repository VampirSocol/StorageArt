package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.services.ArtService;
import org.storageart.storageart.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private ArtService artService;

    private UserMapper userMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setArtService(ArtService artService) {
        this.artService = artService;
    }

    @Autowired
    public void setArtMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

//    @GetMapping("/login")
//    public String loginGet() {
//        return "login";
//    }
//
//    @GetMapping("")
//    public String indexGet() {
//        return "index";
//    }
//
//    @GetMapping("/hello")
//    public String helloGet(Model model) {
//
//        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        model.addAttribute("artsData", artService.getByUser(userData));
//
//        return "hello";
//    }

    @GetMapping("/{userId}/arts")
    public List<ArtData> getArtById(@PathVariable Long userId) throws UserNotFoundByIdException{
        return artService.getByUser(userMapper.toData(userService.findById(userId)));
    }

    @GetMapping("/{userId}")
    public UserData getUserById(@PathVariable Long userId) throws UserNotFoundByIdException {
        return userMapper.toData(userService.findById(userId));
    }

//    @GetMapping("/registration")
//    public String registrationGet(Model model) {
//        model.addAttribute("userData", new UserData());
//        return "registration";
//    }

    @PostMapping("/registration")
    public ResponseEntity<UserData> registrationPost(@RequestBody UserData userData) {
        userService.registration(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(userData);
    }
}
