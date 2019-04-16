package com.launchcode.cheese.controllers;


import com.launchcode.cheese.models.Cheese;
import com.launchcode.cheese.models.CheeseRating;
import com.launchcode.cheese.models.CheeseType;
import com.launchcode.cheese.models.data.CheeseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "cheese")
public class CheeseController {
//    private static ArrayList<Cheese> cheeses = new ArrayList<>();
//    static{
//        Cheese cheddar = new Cheese("Cheddar", "Orange");
//        Cheese feta = new Cheese("Feta", "White");
//        Cheese american = new Cheese("American", "From America");
//        cheeses.add(cheddar);
//        cheeses.add(feta);
//        cheeses.add(american);
//    }
    @RequestMapping
    public String index(Model model){
        model.addAttribute("cheeses", CheeseManager.getAll());
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
        model.addAttribute(new Cheese());
        model.addAttribute("cheeseTypes", CheeseType.values());
        model.addAttribute("cheeseRatings", CheeseRating.values());
        return "cheese/add";
    }

    @RequestMapping(value="remove")
    public String remove(Model model){
        model.addAttribute("cheeses", CheeseManager.getAll());
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
    public String postForm(@ModelAttribute @Valid Cheese newCheese, Errors errors, Model model){
//        cheeses.add(new Cheese(name,description));
        if(errors.hasErrors()){
            model.addAttribute("title", "Add a Cheese");
            model.addAttribute("cheeseTypes", CheeseType.values());
            model.addAttribute("cheeseRatings", CheeseRating.values());
            return "cheese/add";
        }
        CheeseManager.add(newCheese);
        return "redirect:";
    }
    @RequestMapping(value="remove", method=RequestMethod.POST)
    public String postForm(@RequestParam(name="cheeseIDs", required=false) int[] cheeseIDs){
        if(cheeseIDs==null){
            return "redirect:remove";
        }
//        for(String cheeseString:cheeseNames){
//            boolean isCheese = false;
//            int indexToRemove=0;
//            for(Cheese cheese:cheeses){
//                if(cheeses.get(cheeses.indexOf(cheese)).getName().equals(cheeseString)){
//                    isCheese = true;
//                    indexToRemove = cheeses.indexOf(cheese);
//                    break;
//                }
//
//            }
//
////            for(int i=0; i<cheeses.size(); i++){
////
////                if(cheeses.get(i).getName().equals(cheeseString)){
////                    isCheese = true;
////                    indexToRemove = i;
////                    break;
////                }
////            }
//            if(isCheese) {
//                cheeses.remove(cheeses.get(indexToRemove));
//            }
//        }

        CheeseManager.removeMany(cheeseIDs);

        return "redirect:";
    }
    @RequestMapping(value="edit/{cheeseID}")
    public String displayEditForm(Model model, @PathVariable int cheeseID){
        model.addAttribute("action", "../edit");
        model.addAttribute("cheese",CheeseManager.getCheeseByID(cheeseID));
        model.addAttribute("cheeseTypes", CheeseType.values());
        model.addAttribute("cheeseRatings", CheeseRating.values());

        return "cheese/edit";
    }
    @RequestMapping(value="edit", method=RequestMethod.POST)
    public String processEditForm(int cheeseID, @ModelAttribute @Valid Cheese newCheese, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("action", "edit");
            model.addAttribute("keepID", CheeseManager.getCheeseByID(cheeseID).getCheeseID());
            model.addAttribute("cheeseTypes", CheeseType.values());
            model.addAttribute("cheeseRatings", CheeseRating.values());

            return "cheese/edit";
        }
        else{
            Cheese cheese = CheeseManager.getCheeseByID(cheeseID);
            cheese.setName(newCheese.getName());
            cheese.setDescription(newCheese.getDescription());
            cheese.setType(newCheese.getType());
            cheese.setRating(newCheese.getRating());
            return "redirect:";
        }

    }






}
