package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import com.codeup.travelcapstone.repositories.SearchRepository;
import com.codeup.travelcapstone.repositories.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import sun.tools.tree.NewArrayExpression;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private SearchRepository searchRepository;
    private UserController userController;
    private Users users;
    private ReminderRepository reminderRepo;

    @Value("${amadeus.api}")
    String apikey;

    public SearchController(SearchRepository searchRepository, UserController userController, Users users, ReminderRepository reminderRepo) {
        this.searchRepository = searchRepository;
        this.userController = userController;
        this.users=users;
        this.reminderRepo=reminderRepo;
    }

    //get method for the home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("search", new Search());
        model.addAttribute("apikey", apikey);
        return "search/home";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "search/about";
    }

    @GetMapping("/contact")
    public String contactInfo(){return "search/contact";}

    //get method for the info page ... probably the search results
    @PostMapping("/search/home")
    public String search(@ModelAttribute Search search) {
        //  find a flights for the search from the api using that search, put that results in a List<Search>
        //  and passing this list to the view results
        User userSession=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= users.findUsersById(userSession.getId());
        search.setUser(user);
        searchRepository.save(search);
        return "redirect:/";
    }

    @PostMapping("/search/edit")
    public String searchUpdate(@RequestParam Long editSavedSearch, Model model){
        System.out.println(editSavedSearch);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("apikey", apikey);
        model.addAttribute("user", user);
        Search search = searchRepository.findSearchById(editSavedSearch);
        long id = user.getId();
        List<Reminder> reminders=reminderRepo.findAllByUser(id);
        if (reminders.isEmpty()){
            reminders.add(new Reminder());
        }
        model.addAttribute("reminder",new Reminder());
        model.addAttribute("searches", searchRepository.findAllByUser(id));
        model.addAttribute("reminders", reminders);
        model.addAttribute("mySearch",search);
        return "user/dashboard";
    }




//    this controller will redirect the user to the dashboard an will retrieve the updated edited search

    @PostMapping("/search/saveEdit")
    public String saveUpdate(@ModelAttribute Search mySearch){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mySearch.setUser(user);
        searchRepository.save(mySearch);
        return "redirect:/dashboard";
    }




//    this controller will redirect the user to the search view and will display the the results for this edited search

//    @PostMapping("/search/saveEdit")
//    public String saveUpdate(@ModelAttribute Search mySearch, Model model){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        mySearch.setUser(user);
//        model.addAttribute("search",mySearch);
//        return "search/home";
//    }





    @PostMapping("/search/deleteSearch")
    public String delete(@RequestParam Long deleteSearch){
        System.out.println("id from search to be deleted :"+deleteSearch);
        searchRepository.delete(deleteSearch);
//        searchRepository.deleteSearchById(deleteSearch);
        return "redirect:/dashboard";
    }



    @GetMapping("/poi")
    public String poi(){
        return "/search/poi";
    }


}
