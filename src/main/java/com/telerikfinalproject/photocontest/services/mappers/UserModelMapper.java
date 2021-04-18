package com.telerikfinalproject.photocontest.services.mappers;

import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.JuryDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserUpdateDto;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    public static final int DEFAULT_SCORE = 0;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserModelMapper(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User userDtoToUser(UserDto userDto) {
        Credential credential = new Credential(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), UserRole.PHOTO_JUNKIE);
        return new User(userDto.getFirstName(), userDto.getLastName(), credential, DEFAULT_SCORE);
    }

    public User userDtoToUser(UserUpdateDto userDto, int userId) {
        User user = userService.getUserById(userId);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }

    public UserOutputDto userToDto(User user) {

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setFirstName(user.getFirstName());
        userOutputDto.setLastName(user.getLastName());
        userOutputDto.setRole(user.getCredential().getUserRole().toString());
        userOutputDto.setScore(user.getScore());
        userOutputDto.setId(user.getId());

        return userOutputDto;

    }

    public JuryDto userToJuryDto(User user) {

        JuryDto juryDto = new JuryDto();
        juryDto.setName(user.getFirstName() + " " + user.getLastName());
        juryDto.setScore(user.getScore());
        juryDto.setUserID(user.getId());
        return juryDto;
    }


    public UserUpdateDto userToUpdateDto(User user) {

        UserUpdateDto userDto = new UserUpdateDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;

    }
}
