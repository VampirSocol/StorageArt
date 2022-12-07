package org.storageart.storageart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.services.ArtService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ArtController {

    private ArtService artService;

    @Value("${upload.path}")
    private String UPLOAD_PATH;

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
                             Model model,
                             BindingResult bindingResult) throws IOException, UserNotFoundByIdException {

        artService.uploadDirExists(UPLOAD_PATH);

        //ArtData artData = new ArtData();
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        artData.setUserId(userData.getId());
        artData.setName(file.getOriginalFilename());
        //Path fileNameAndPath = Paths.get(UPLOAD_PATH, file.getOriginalFilename());
        //Files.write(fileNameAndPath, file.getBytes());
        file.transferTo(new File(file.getOriginalFilename()));
        artService.save(artData);
        model.addAttribute("msg", "Uploaded images: " + file.getOriginalFilename().toString());
        return "redirect:/hello";
     }

}