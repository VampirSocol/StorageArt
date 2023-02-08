package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.services.ArtService;
import org.storageart.storageart.services.UserService;

import java.io.IOException;
import java.util.UUID;

@Controller
public class ArtController {

    private ArtService artService;

    private UserService userService;

    @Autowired
    public void setArtService(ArtService artService) {
        this.artService = artService;
    }

    @Autowired
    public void setUserService(){
        this.userService = userService;
    }

    @GetMapping("/addart")
    public String addArtGet(Model model){
        model.addAttribute("artData", new ArtData());
        return "addart";
    }

    @PostMapping("/addart")
    public String addArtPost(@RequestParam("image")MultipartFile file,
                             @ModelAttribute("artData") ArtData artData,
                             Model model) throws IOException, UserNotFoundByIdException {

        artData.setUserId(userService.getUserOutOfContext().getId());

        artService.saveToDirectory(file, artData);
        artService.saveToRepository(artData);

        model.addAttribute("msg", "Uploaded images: " + file.getOriginalFilename());
        return "redirect:/hello";
     }

}