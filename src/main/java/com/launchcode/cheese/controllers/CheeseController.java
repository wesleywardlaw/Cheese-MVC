package com.launchcode.cheese.controllers;


import com.launchcode.cheese.models.*;
import com.launchcode.cheese.models.data.CategoryDAO;
import com.launchcode.cheese.models.data.CheeseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.ManyToMany;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "cheese")
public class CheeseController {

    @ManyToMany(mappedBy = "cheeses")
    private List<Menu> menus;

    @Autowired
    private CheeseDAO cheeseDAO;

    @Autowired
    private CategoryDAO categoryDAO;
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
        model.addAttribute("cheeses", cheeseDAO.findAll());
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
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("cheeseRatings", CheeseRating.values());
        return "cheese/add";
    }

    @RequestMapping(value="remove")
    public String remove(Model model){
        model.addAttribute("cheeses", cheeseDAO.findAll());
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
    public String postForm(@ModelAttribute @Valid Cheese newCheese, Errors errors, Model model, @RequestParam long categoryID){
//        cheeses.add(new Cheese(name,description));
        if(errors.hasErrors()){
            model.addAttribute("title", "Add a Cheese");
//            model.addAttribute("cheese",newCheese);
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("cheeseRatings", CheeseRating.values());
            return "cheese/add";
        }
        Category cat = categoryDAO.findOne(categoryID);
        newCheese.setCategory(cat);
        cheeseDAO.save(newCheese);
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
       cheeseDAO.deleteMany(cheeseIDs);


        return "redirect:";
    }
    @RequestMapping(value="edit/{cheeseID}")
    public String displayEditForm(Model model, @PathVariable int cheeseID){
        model.addAttribute("action", "../edit");
        model.addAttribute("cheese",cheeseDAO.findOne(cheeseID));
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("cheeseRatings", CheeseRating.values());

        return "cheese/edit";
    }
    @RequestMapping(value="edit", method=RequestMethod.POST)
    public String processEditForm( @ModelAttribute @Valid Cheese newCheese, Errors errors, Model model, @RequestParam long categoryID){

        if(errors.hasErrors()){
            model.addAttribute("action", "edit");
//            model.addAttribute("keepCheeseID",cheeseID);

            model.addAttribute("keepCategoryID", categoryID);
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("cheeseRatings", CheeseRating.values());

            return "cheese/edit";
        }

            Category cat = categoryDAO.findOne(categoryID);
            newCheese.setCategory(cat);
            cheeseDAO.save(newCheese);
            return "redirect:";


    }

    @RequestMapping(value="category/{categoryID}")
    public String category(@PathVariable long categoryID, Model model){
        Iterable<Cheese> cheeses = cheeseDAO.findAll();
        ArrayList<Cheese> sent = new ArrayList<Cheese>();
        for(Cheese cheese:cheeses){
            if(categoryDAO.findOne(categoryID)!=null) {
                if (cheese.getCategory().equals(categoryDAO.findOne(categoryID))) {
                    sent.add(cheese);
                }
            } else{
                model.addAttribute("cheeses", cheeseDAO.findAll());
                return "cheese/index";
            }

        }
        model.addAttribute("cheeses", sent);
    return "cheese/index";
    }




}
