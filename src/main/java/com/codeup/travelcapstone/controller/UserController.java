package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import com.codeup.travelcapstone.repositories.SearchRepository;
import com.codeup.travelcapstone.repositories.UserRepository;
import com.codeup.travelcapstone.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private Users users;
    private PasswordEncoder passwordEncoder;
    private SearchRepository searchRepo;
    private ReminderRepository reminderRepo;

//
//    public UserController(UserRepository users, PasswordEncoder passwordEncoder) {
//
//    }



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
        model.addAttribute("user", user);
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


    @GetMapping("/profile")
    public String profile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/profile";}



}
