package org.storageart.storageart.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.storageart.storageart.dto.ArtData;
import org.storageart.storageart.entities.Art;
import org.storageart.storageart.exceptions.UserNotFoundByIdException;
import org.storageart.storageart.repositories.UserRepository;
import org.storageart.storageart.services.UserService;

@Component
public class ArtMapper {

    private UserRepository userRepository;

    private UserService userService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Art toEntity(ArtData artData) throws UserNotFoundByIdException {
        Art art = new Art();

        art.setId(artData.getId());
        art.setName(artData.getName());
        art.setUser(userService.findById(artData.getUserId()));

        return art;
    }

    public ArtData toData(Art art){
        ArtData artData = new ArtData();

        artData.setId(art.getId());
        artData.setName(art.getName());
        artData.setUserId(art.getUser().getId());

        return artData;
    }

}
