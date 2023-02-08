package org.storageart.storageart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.entities.Art;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.mapper.ArtMapper;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.repositories.ArtRepository;
import org.storageart.storageart.repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ArtService {

    private ArtRepository artRepository;

    private UserRepository userRepository;

    private ArtMapper artMapper;

    private UserMapper userMapper;

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

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void saveToRepository(ArtData artData) throws UserNotFoundByIdException {
        artRepository.save(artMapper.toEntity(artData));
    }

    public void saveToDirectory(MultipartFile file, ArtData artData) throws IOException {
        String uuid = UUID.randomUUID().toString();
        artData.setName("/images/" + uuid + file.getOriginalFilename());

        Path fileNameAndPath = Paths.get(UPLOAD_PATH, uuid + file.getOriginalFilename()).toAbsolutePath();
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            Path uploadPath = Paths.get(UPLOAD_PATH);
            Files.createDirectories(uploadPath);
        }
        Files.write(fileNameAndPath.toAbsolutePath(), file.getBytes());
    }

    public List<ArtData> getByUser(UserData userData) {
        List<Art> arts = artRepository.findByUser(userMapper.toEntity(userData));

        List<ArtData> artsData = new ArrayList<>();

        for (Art art : arts) {
            artsData.add(artMapper.toData(art));
        }

        return artsData;
    }

}