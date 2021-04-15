package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.UserDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikfinalproject.photocontest.controllers.utils.Constants.*;

@Api(tags = {"users"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserModelMapper mapper;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public UserController(UserService userService, UserModelMapper mapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.mapper = mapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ApiOperation(value = ALL_USERS, response = List.class)
    @GetMapping
    public List<UserOutputDto> getAllUsers(@RequestHeader HttpHeaders headers) {
        Credential credential = authenticationHelper.getUserCredentials(headers);
        return userService.getAllUsers(credential).stream()
                .map(mapper::userToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = USER_BY_ID , response = User.class)
    @GetMapping("/{id}")
    public UserOutputDto getUserById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        Credential credential = authenticationHelper.getUserCredentials(headers);
        return mapper.userToDto(userService.getUserById(id, credential));
    }

    @ApiOperation(value = CREATE_USER , response = User.class)
    @PostMapping
    public User createUser(@Valid @RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        userService.createUser(user);
        return user;
    }



}
