package coworkingApp.controller;

import coworkingApp.entity.User;
import coworkingApp.model.UserInputModel;
import coworkingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Страница со списком всех пользователей
    @GetMapping
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    // Форма для добавления пользователя
    @PostMapping
    public ResponseEntity<User> addUser(
            @Valid @RequestBody UserInputModel userInput
    ) {
        try {
            User user = userService.addUser(userInput);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
