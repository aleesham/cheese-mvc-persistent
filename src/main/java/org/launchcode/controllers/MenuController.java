package org.launchcode.controllers;

import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
