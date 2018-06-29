package com.codeup.travelcapstone.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/")
    public String homepage(){
        return "search/home";
    }



}
