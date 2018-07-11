package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import com.codeup.travelcapstone.repositories.SearchRepository;
import com.codeup.travelcapstone.repositories.UserRepository;
import com.codeup.travelcapstone.repositories.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@Controller
public class UserController {
    private Users users;
    private PasswordEncoder passwordEncoder;
    private SearchRepository searchRepo;
    private ReminderRepository reminderRepo;
    @Value("${amadeus.api}")
    String apikey;



    public UserController(Users users, PasswordEncoder passwordEncoder, SearchRepository searchRepo, ReminderRepository reminderRepo) {
        this.searchRepo = searchRepo;
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.reminderRepo = reminderRepo;
    }



    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "user/registration";
    }


    @PostMapping("/sign-up")
    public String createUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        return  "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("apikey", apikey);
        model.addAttribute("user", user);
        model.addAttribute("mySearch",new Search());
        long id = user.getId();
        model.addAttribute("searches", searchRepo.findAllByUser(id));
        model.addAttribute("reminder", new Reminder());
        model.addAttribute("reminders", reminderRepo.findAllByUser(id));
        return "user/dashboard";
    }

    @PostMapping("/dashboard")
    public String saveReminder(@ModelAttribute Reminder reminder){
        reminder.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reminderRepo.save(reminder);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(){

        return "user/login";}

    @GetMapping("/logout")
    public String logout(){

        return "redirect:/login";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/profile";}




    @PostMapping("/profile")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam String newPassword, @RequestParam String confirmNewPassword ) {
        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1=users.findUsersById(userSession.getId());
        boolean isAMatch =(newPassword.equals(confirmNewPassword));
        System.out.println(user1.getUsername());
        user1.setPhonenumber(user.getPhonenumber());
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        if (!user.getPassword().equals(user1.getPassword())){
            if (isAMatch){
                user1.setPassword(passwordEncoder.encode(newPassword));
            }
            else {
                return "user/profile";
            }
        }
        else {
            return "user/profile";
        }

        users.save(user1);
        return "user/profile";
    }

}
