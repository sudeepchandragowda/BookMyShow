package com.scaler.BookMyShow.controller;

import com.scaler.BookMyShow.dto.UserSignUpRequestDTO;
import com.scaler.BookMyShow.dto.UserSignUpResponseDTO;
import com.scaler.BookMyShow.models.Users;
import com.scaler.BookMyShow.services.UserService;
import com.scaler.BookMyShow.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO userSignUpRequestDTO){
        Users user;
        UserSignUpResponseDTO responseDTO = new UserSignUpResponseDTO();
        try{
            user = userService.signUp(userSignUpRequestDTO.getName(), userSignUpRequestDTO.getEmail(), userSignUpRequestDTO.getPassword());

        }
    }
}
