package com.launchcode.cheese.controllers;


import com.launchcode.cheese.models.Cheese;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "cheese")
public class CheeseController {
    private static ArrayList<Cheese> cheeses = new ArrayList<>();
    static{
        Cheese cheddar = new Cheese("Cheddar", "Orange");
        Cheese feta = new Cheese("Feta", "White");
        Cheese american = new Cheese("American", "From America");
        cheeses.add(cheddar);
        cheeses.add(feta);
        cheeses.add(american);


    }
    @RequestMapping
    public String index(Model model){
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "My Cheese");
        return "cheese/index";
    }

    //add a handler for the form

    //controller(route handler for the add form)
    //path and method for the form
        //path is "add" and method is "GET"
    //give the template a title
    //render the template
    @RequestMapping(value="add")
    public String form(Model model){
        model.addAttribute("title", "Add a Cheese");
        return "cheese/add";
    }

    @RequestMapping(value="remove")
    public String remove(Model model){
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Remove Cheeses");
        return "cheese/remove";
    }

    //view
    //display the title variable
    //display the form
        //action and a method
            //path is "add" and method is "POST"
        //text input for the name of the cheese
        //submit input

    //Controller(route handler for the add submission)
        //path and method where the form is being sent
            //path is add and method is POST
        //extract the cheeseName data from the request
        //add it to the list of cheeses
        //redirect to list of cheeses (index)

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String postForm(@RequestParam(name="name", required=false) String name, @RequestParam(name="description", required=false) String description ){
        cheeses.add(new Cheese(name,description));
        return "redirect:";
    }
    @RequestMapping(value="remove", method=RequestMethod.POST)
    public String postForm(@RequestParam(name="cheeseNames", required=false) String[] cheeseNames){
        if(cheeseNames==null){
            return "redirect:remove";
        }
        for(String cheeseString:cheeseNames){
            boolean isCheese = false;
            int indexToRemove=0;
            for(Cheese cheese:cheeses){
                if(cheeses.get(cheeses.indexOf(cheese)).getName().equals(cheeseString)){
                    isCheese = true;
                    indexToRemove = cheeses.indexOf(cheese);
                    break;
                }

            }

//            for(int i=0; i<cheeses.size(); i++){
//
//                if(cheeses.get(i).getName().equals(cheeseString)){
//                    isCheese = true;
//                    indexToRemove = i;
//                    break;
//                }
//            }
            if(isCheese) {
                cheeses.remove(cheeses.get(indexToRemove));
            }
        }

        return "redirect:";
    }






}
