package com.launchcode.cheese.controllers;


import com.launchcode.cheese.models.Cheese;
import com.launchcode.cheese.models.Menu;
import com.launchcode.cheese.models.data.CheeseDAO;
import com.launchcode.cheese.models.data.MenuDAO;
import com.launchcode.cheese.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value="menu")
public class MenuController {
    @Autowired
    MenuDAO menuDAO;

    @Autowired
    CheeseDAO cheeseDAO;

    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("menus", menuDAO.findAll());
        model.addAttribute("title", "List of Menus");
        return "menu/index";
    }

    @RequestMapping(value="add")
    public String addForm(Model model, Menu menu){
        model.addAttribute("title", "Add a Menu");
        return "menu/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Menu menu, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add a Menu");
            return "menu/add";
        }
        menuDAO.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value="view/{menuID}")
    public String viewMenu(Model model, @PathVariable int menuID){
        Menu menu = menuDAO.findOne(menuID);
        model.addAttribute("menu", menu);
        model.addAttribute("title", menu.getName());
        return "menu/view";
    }

    @RequestMapping(value="add-item/{menuID}")
    public String addItem(Model model, @PathVariable int menuID){
        Menu menu = menuDAO.findOne(menuID);
        System.out.println(menu.getId());
        AddMenuItemForm addMenuItemForm = new AddMenuItemForm(menu, cheeseDAO.findAll());
        model.addAttribute("form", addMenuItemForm);
        model.addAttribute("title", "Add item to menu: " + menu.getName());
        return "menu/add-item";
    }

    @RequestMapping(value="add-item", method=RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm addMenuItemForm, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "there has been a mistake!");
            return "menu/add-item";
        }
        int cheeseID = addMenuItemForm.getCheeseID();
        int menuID = addMenuItemForm.getMenuID();
        Cheese cheese = cheeseDAO.findOne(cheeseID);
        Menu menu = menuDAO.findOne(menuID);
        menu.addItem(cheese);
        menuDAO.save(menu);
        return "redirect:view/" + menu.getId();
    }

}
