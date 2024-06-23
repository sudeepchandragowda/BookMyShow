package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.exception.InvalidCredentialsException;
import com.scaler.BookMyShow.exception.UserAlreadyExistsException;
import com.scaler.BookMyShow.exception.UserNotFoundException;
import com.scaler.BookMyShow.models.Users;
import com.scaler.BookMyShow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users login(String email, String password) {
        Optional<Users> usersOptional = userRepository.findByEmail(email);
        if (usersOptional.isEmpty()) {
            throw new UserNotFoundException("User with given email doesn't exist");
        }
        Users users = usersOptional.get();
        if (users.getPassword().equals(password)) {
            return users;
        } else {
            throw new InvalidCredentialsException("Credentials are invalid");
        }
    }

    @Override
    public Users signUp(String name, String email, String password) {
        Optional<Users> usersOptional = userRepository.findByEmail(email);
        if (usersOptional.isPresent()) {
            throw new UserAlreadyExistsException("User with given email is already present");
        }
        Users newUser = new Users();
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setEmail(email);
        return userRepository.save(newUser);
        }
}
