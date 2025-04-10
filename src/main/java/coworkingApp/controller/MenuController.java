package coworkingApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {

    @GetMapping
    public String showMainMenu() {
        return "mainMenu";
    }

    @GetMapping("/userMenu")
    public String showUserMenu() {
        return "userMenu";
    }
    @GetMapping("/adminMenu")
    public String showAdminMenu() {
        return "adminMenu";
    }

}
