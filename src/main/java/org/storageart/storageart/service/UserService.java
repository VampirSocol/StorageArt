package org.storageart.storageart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.storageart.storageart.dto.UserData;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userMapper.toData(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user with username " + username)));
    }

    public void saveUser(UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(userMapper.toEntity(userData));
    }

    public boolean isUserExist(UserData userData) {
        return userRepository.existsByUsername(userData.getUsername());
    }
}