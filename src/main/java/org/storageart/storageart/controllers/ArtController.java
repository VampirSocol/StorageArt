package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.services.ArtService;
import org.storageart.storageart.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/arts")
public class ArtController {

    private ArtService artService;

    private UserService userService;

    private UserMapper userMapper;

    @Autowired
    public void setArtService(ArtService artService) {
        this.artService = artService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    @GetMapping("/addArt")
//    public String addArtGet(Model model){
//        model.addAttribute("artData", new ArtData());
//        return "addart";
//    }

    @PostMapping("/addArt")
    public ResponseEntity<ArtData> addArtPost(@RequestParam/*("image")*/MultipartFile file)
            throws IOException, UserNotFoundByIdException {

        ArtData artData = artService.saveToDirectory(file);
        artService.saveToRepository(artData);
        return ResponseEntity.status(HttpStatus.CREATED).body(artData);

     }

}