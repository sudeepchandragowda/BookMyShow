package com.scaler.BookMyShow.controller;

import com.scaler.BookMyShow.dto.UserSignUpRequestDTO;
import com.scaler.BookMyShow.dto.UserSignUpResponseDTO;
import com.scaler.BookMyShow.services.UserService;
import com.scaler.BookMyShow.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    public UserSignUpResponseDTO signUo(UserSignUpRequestDTO userSignUpRequestDTO){

    }
}
