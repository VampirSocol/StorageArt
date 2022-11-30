package org.storageart.storageart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userMapper.toData(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user with username " + username)));
    }

    public void addUser(UserData userData) {
        userRepository.save(userMapper.toEntity(userData));
    }

    public boolean isUserExist(UserData userData) {
        return userRepository.findByUsername(userData.getUsername()).isPresent();
    }
}