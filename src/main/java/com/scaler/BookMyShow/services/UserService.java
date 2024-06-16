package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.models.Users;

public interface UserService {
    Users login(String email, String password);
    Users signUp(String name, String email, String password);
}
