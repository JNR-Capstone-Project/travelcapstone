package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.SearchRepository;
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
    @Value("${amadeus.api}")
    String apikey;

    public SearchController(SearchRepository searchRepository, UserController userController) {
        this.searchRepository = searchRepository;
        this.userController = userController;
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
        //  find a flies for the search from the api using that search, put that results in a List<Search>
        //  and passing this list to the view results
        search.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        searchRepository.save(search);
        return "redirect:/";
    }

    @GetMapping("/search/edit")
    public String searchUpdate(@RequestParam Long id, Model model){
        Search search=searchRepository.findSearchById(id);
        model.addAttribute(search);
        return "user/dashboard";
    }


    @GetMapping("/poi")
    public String poi(){
        return "/search/poi";
    }


}
