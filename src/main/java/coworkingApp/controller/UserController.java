package coworkingApp.controller;

import coworkingApp.entity.Booking;
import coworkingApp.model.UserInputModel;
import coworkingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    // Страница со списком всех пользователей
    @GetMapping("/allUsers")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    // Форма для добавления пользователя
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("userInput", new UserInputModel());
        return "addUser";
    }

    // Обработка формы добавления пользователя
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("userInput") @Valid UserInputModel userInput,
                          BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addUser";
        }

        userService.addUser(
                userInput.getName(),
                userInput.getSurname(),
                userInput.getEmail(),
                userInput.getUserType()
        );
        redirectAttributes.addFlashAttribute("successUser", "User added successfully");
        return "redirect:/addUser";
    }


}
