package com.ffjm.photoapp.usermicroservice.service;

import com.ffjm.photoapp.usermicroservice.dto.UserDto;
import com.ffjm.photoapp.usermicroservice.entity.UserEntity;
import com.ffjm.photoapp.usermicroservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassWord()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        repository.save(userEntity);
        return modelMapper.map(repository.save(userEntity), UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = repository.findByEmail(email);
        if(entity == null){
            throw new UsernameNotFoundException(email);
        }
        return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
