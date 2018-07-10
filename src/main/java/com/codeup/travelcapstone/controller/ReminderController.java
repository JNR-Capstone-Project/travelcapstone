package com.codeup.travelcapstone.controller;

import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReminderController {
    private ReminderRepository reminderRepo;

    public ReminderController(ReminderRepository reminderRepo){
        this.reminderRepo = reminderRepo;
    }

//    @GetMapping("/dashboard")
//    public String setReminder(Model model){
//        model.addAttribute("reminder", new Reminder());
//        return "user/dashboard";
//    }



}
