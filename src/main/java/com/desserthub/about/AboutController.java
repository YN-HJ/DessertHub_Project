package com.desserthub.about;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping
    public String getMethodName() {
        return "about";
    }

    @PostMapping("/contact")
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }
    
}
