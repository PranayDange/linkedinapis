package com.codingshuttle.linkedin.user_service.service;

import com.codingshuttle.linkedin.user_service.dto.LoginRequestDto;
import com.codingshuttle.linkedin.user_service.dto.SignUpRequestDto;
import com.codingshuttle.linkedin.user_service.dto.UserDto;
import com.codingshuttle.linkedin.user_service.entity.User;
import com.codingshuttle.linkedin.user_service.exception.BadRequestException;
import com.codingshuttle.linkedin.user_service.exception.ResourceNotFoundException;
import com.codingshuttle.linkedin.user_service.reposiotry.UserRepository;
import com.codingshuttle.linkedin.user_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = modelMapper.map(signUpRequestDto, User.class);
        user.setPassword(PasswordUtils.hashPassword(signUpRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);


    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect Password");
        }
        //but if password is matched and user has successfully logged in in this case we are going to generate the token
        return jwtService.generateAccessToken(user);
    }


}
