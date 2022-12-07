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

import java.io.IOException;

@Controller
public class ArtController {

    private ArtService artService;

    @Autowired
    public void setArtService(ArtService artService) {
        this.artService = artService;
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

        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        artData.setUserId(userData.getId());
        artData.setName(file.getOriginalFilename());

        artService.saveToDirectory(file);
        artService.saveToRepository(artData);

        model.addAttribute("msg", "Uploaded images: " + file.getOriginalFilename());
        return "redirect:/hello";
     }

}