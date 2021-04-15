package com.telerikfinalproject.photocontest.security;

import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final UserModelMapper userModelMapper;
    private final UserService userService;


    @Autowired
    public AuthenticationSuccessHandlerImpl(UserModelMapper userModelMapper, UserService userService) {
        this.userModelMapper = userModelMapper;
        this.userService = userService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        UserOutputDto loggedUser = userModelMapper.userToDto(userService.getUserByUsername(authentication.getName()));
        session.setAttribute("loggedUser", loggedUser);
        response.sendRedirect(request.getContextPath());

    }
}
