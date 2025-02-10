package com.desserthub.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String request_home(Model model) {
        String username = (String) model.getAttribute("username");
        if (username != null) {
            model.addAttribute("welcomeMessage", "Welcome, " + username + "!");
        }

        return "home";
    }

    @GetMapping("/remessage")
    public String redirect_handler() {
        return "remessage";
    }

    @GetMapping("/map")
    public String getMethodName() {
        return "map";
    }
}
