package com.ivlai.note.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("")
    public String index(
            String info
            , Model model
    ) {
        model.addAttribute("info", info);
        return "index";
    }

}
