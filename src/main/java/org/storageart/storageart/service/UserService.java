package org.storageart.storageart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.storageart.storageart.mapper.UserMapper;
import org.storageart.storageart.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userMapper.toData(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user with username " + username)));
    }

}