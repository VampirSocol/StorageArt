package org.storageart.storageart.mapper;

import org.springframework.stereotype.Component;

import org.storageart.storageart.entities.User;
import org.storageart.storageart.dto.UserData;

@Component
public class UserMapper {

    public User toEntity(UserData userData){
        User user = new User();

        user.setId(userData.getId());
        user.setPassword(userData.getPassword());
        user.setUsername(userData.getUsername());

        return user;
    }

    public UserData toData(User user){
        UserData userData = new UserData();

        userData.setId(user.getId());
        userData.setPassword(user.getPassword());
        userData.setUsername(user.getUsername());

        return userData;
    }

}
