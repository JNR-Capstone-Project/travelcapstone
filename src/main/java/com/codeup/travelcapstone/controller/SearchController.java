package com.codeup.travelcapstone.controller;


import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
import com.codeup.travelcapstone.repositories.SearchRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sun.tools.tree.NewArrayExpression;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private SearchRepository searchRepository;
    private UserController userController;

    public SearchController(SearchRepository searchRepository, UserController userController) {
        this.searchRepository = searchRepository;
        this.userController = userController;
    }

    //get method for the home page
    @GetMapping("/")
    public String home() {
        return "search/home";
    }

    //get method for the info page ... probably the search results
//    @PostMapping("/search")
//    public String search(@ModelAttribute Search search) {
//        //  find a flies for the search from the api using that search, put that results in a List<Search>
//        //  and passing this list to the view results
//        return "/search/results";
//    }

    //post method for the search will submit a list of search objects and will be passed to the results view
    @GetMapping("/results")
    public String viewResults(@ModelAttribute List<Search> results, Model view) {
        view.addAttribute("", results);
        return "/search/results";
    }


//    @PostMapping("/home/search/results/save")
//    public String saveResults(@ModelAttribute Search search) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user != null) {
//            //if the user is logged should be able to save the results as search
//            ArrayList<Search> toSave = new ArrayList<>();
//            toSave.add(search);
////            searchRepository.save()
//        } else {
//            //redirect the user to the registration view if there's a view for
//        }
//
//        return "/search/results";
//    }
}
