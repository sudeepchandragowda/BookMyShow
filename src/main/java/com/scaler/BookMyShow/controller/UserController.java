package com.scaler.BookMyShow.controller;

import com.scaler.BookMyShow.controller.utils.UserControllerUtil;
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
            UserControllerUtil.validateUserSignUpRequestDTO(userSignUpRequestDTO);
            user = userService.signUp(userSignUpRequestDTO.getName(), userSignUpRequestDTO.getEmail(), userSignUpRequestDTO.getPassword());
            // method that converts internal models into DTOs
            responseDTO.setId(user.getId());
            responseDTO.setName(user.getName());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setTickets(user.getTickets());
            responseDTO.setResponseCode(200);
            responseDTO.setResponseMessage("SUCCESS");
            return responseDTO;
        } catch (Exception e) {
            responseDTO.setResponseCode(500);
            responseDTO.setResponseMessage("Internal server error");
            return responseDTO;
        }
    }
}
