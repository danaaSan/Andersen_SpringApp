package coworkingApp.controller;

import coworkingApp.entity.Booking;
import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.user.User;
import coworkingApp.model.SpaceInputModel;
import coworkingApp.model.UserInputModel;
import coworkingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    // Страница со списком всех пользователей
    @GetMapping("/allUsers")
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    // Форма для добавления пользователя
    @GetMapping("/addUser")
    public ResponseEntity<User> addUser(
            @Valid @RequestBody UserInputModel userInput
    ) {
        try {
            User user = userService.addUser(
                    userInput.getName(),
                    userInput.getSurname(),
                    userInput.getEmail(),
                    userInput.getUserType());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
