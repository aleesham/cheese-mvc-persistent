package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by alees on 7/20/2017.
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    MenuDao menuDao;

    @Autowired
    CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model){
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(Model model, @ModelAttribute @Valid Menu
            newMenu, Errors errors) {

        if (errors.hasErrors()){
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int id, Model model){
        model.addAttribute("menu", menuDao.findOne(id));
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(@PathVariable int id, Model model){
        //Right now, I think that cheeseDao.findAll() is not passing the complete list of cheese, but I am unsure why.
        //It is passing the complete list, but the constructor is
        // somehow messing up or it is getting messed up in my
        // html, which I think is more likely.
        AddMenuItemForm newForm = new AddMenuItemForm(menuDao.findOne(id), cheeseDao.findAll());
        model.addAttribute("form", newForm);

        //Just trying something here to see if cheeseDao.findAll() returns anything.
        //This does return something! I used the view to check this attribute versus the newForm.cheeses that was built from the same thing and we are
        // having issues. Not sure what is happening.
        model.addAttribute("Cheeses", cheeseDao.findAll());

        model.addAttribute("title", "Add item to menu: " +
                menuDao.findOne(id).getName());
        return "menu/add-item";
    }
}
