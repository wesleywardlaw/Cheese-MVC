package com.launchcode.cheese.controllers;

import com.launchcode.cheese.models.Category;
import com.launchcode.cheese.models.data.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "category")
public class CategoryController {
    @Autowired
    private CategoryDAO categoryDAO;

    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("title", "View Categories");
        model.addAttribute("categories", categoryDAO.findAll());
        return "category/index";
    }

    @RequestMapping(value="add")
    public String addForm(Model model){
        model.addAttribute("title", "New Category");
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
        public String processAddForm(Model model, @ModelAttribute @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("title", "New Category");
            return "category/add";
        }
        categoryDAO.save(category);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("title", "Categories");
        return "category/index";
    }
}
