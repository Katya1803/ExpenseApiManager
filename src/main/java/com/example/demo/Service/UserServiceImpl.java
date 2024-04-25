package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Entity.UserModel;
import com.example.demo.ExceptionHandling.ResourceNotFoundException;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;
    @Override
    public User createUser(UserModel user)
    {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userRepo.save(newUser);
    }

    @Override
    public User readUser()
    {
        Long id = getLoggedInUser().getId();
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public User updateUser(UserModel user)
    {
        User existingUser = readUser();
        existingUser.setName(user.getName() != null? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null? user.getPassword() : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null? user.getAge() : existingUser.getAge());
        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser()
    {
        User user = readUser();
        userRepo.delete(user);
    }

    @Override
    public User getLoggedInUser()
    {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email "+email));
    }
}
