package org.storageart.storageart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.mapper.ArtMapper;
import org.storageart.storageart.repositories.ArtRepository;
import org.storageart.storageart.repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArtService {

    private ArtRepository artRepository;

    private UserRepository userRepository;

    private ArtMapper artMapper;

    @Value("${upload.path}")
    private String UPLOAD_PATH;

    @Autowired
    public void setArtRepository(ArtRepository artRepository) {
        this.artRepository = artRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setArtMapper(ArtMapper artMapper) {
        this.artMapper = artMapper;
    }

    public void saveToRepository(ArtData artData) throws UserNotFoundByIdException {
        artRepository.save(artMapper.toEntity(artData));
    }

    public void saveToDirectory(MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_PATH, file.getOriginalFilename()).toAbsolutePath();
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            Path uploadPath = Paths.get(UPLOAD_PATH);
            Files.createDirectories(uploadPath);
        }
        Files.write(fileNameAndPath.toAbsolutePath(), file.getBytes());
    }

}