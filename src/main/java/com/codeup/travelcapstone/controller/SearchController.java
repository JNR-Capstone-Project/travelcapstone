package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
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
    @Value("${amadeus.api}")
    String apikey;

    public SearchController(SearchRepository searchRepository, UserController userController, Users users) {
        this.searchRepository = searchRepository;
        this.userController = userController;
        this.users=users;
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
        Search search = searchRepository.findSearchById(editSavedSearch);
        model.addAttribute("mySearch",search);
        return "redirect:/dashboard";
    }


    @GetMapping("/poi")
    public String poi(){
        return "/search/poi";
    }


}
