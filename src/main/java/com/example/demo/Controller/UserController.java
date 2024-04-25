package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Entity.UserModel;
import com.example.demo.ExceptionHandling.ResourceNotFoundException;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;


    //Read by id
    @GetMapping("/profile")
    public ResponseEntity<User> readUser(@PathVariable Long id){
        return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
    }

    //Update user with id = ?
    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user, @PathVariable Long id){
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }

    //Delete by id
    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser() throws ResourceNotFoundException
    {
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
