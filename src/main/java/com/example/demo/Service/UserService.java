package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Entity.UserModel;
import org.springframework.stereotype.Service;


public interface UserService
{
    User createUser(UserModel user);

    User readUser();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();
}
