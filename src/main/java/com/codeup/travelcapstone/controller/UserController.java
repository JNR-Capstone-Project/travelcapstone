package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private UserRepository users;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder) {




        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/home/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }


    @PostMapping("/home/sign-up")
    public String createUser(@ModelAttribute User user, PasswordEncoder passwordEncoder){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        return  "home/login";
    }



}
