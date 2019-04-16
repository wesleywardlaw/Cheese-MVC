package com.launchcode.cheese.controllers;

import com.launchcode.cheese.models.User;
import com.launchcode.cheese.models.data.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.launchcode.cheese.utility.isAlpha;

import java.util.Collection;

@Controller
@RequestMapping(value="user")
public class UserController {

    @RequestMapping(value="add")
    public String add(Model model){
        model.addAttribute("title", "Add a User");
        return "user/add";
    }



    @RequestMapping(value="add", method=RequestMethod.POST)
    public String add(Model model, @ModelAttribute User user, String verify){
        UserManager.add(user);
        model.addAttribute("title", "Users");
        model.addAttribute("user", user);
        StringBuilder error = new StringBuilder();
        System.out.println("This is the username" + user.getUsername());
        if(user.getUsername().equals("")){
            error.append("<p>Please enter a username.</p>");

        }
        if(user.getUsername().length()<5||user.getUsername().length()>15){
            error.append("<p>Usernames must be between 5 and 15 characters.</p>");
        }
        if(!(isAlpha.isAlpha(user.getUsername()))){
            error.append("<p>Usernames must only contain letters.</p>");
        }
        if(user.getEmail().equals("")){
            error.append("<p>Please enter an email address.</p>");
        }

        if(!user.getPassword().equals(verify)){
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            error.append("<p>Password and verify must match.</p>");
        }
        String stringError = error.toString();
        if(stringError.length()>0){
            model.addAttribute("error", stringError);
            return "user/add";
        }
        model.addAttribute("users", UserManager.getAll());
        System.out.println(UserManager.getAll());
        return "user/index";

    }
    @RequestMapping("details/{userID}")
    public String details(Model model, @PathVariable int userID){
        model.addAttribute("title", "User Details");
        model.addAttribute("user",UserManager.getUserByID(userID));
        return "user/details";
    }

}
