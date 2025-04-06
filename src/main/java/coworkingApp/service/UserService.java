package coworkingApp.service;

import coworkingApp.entity.user.Admin;
import coworkingApp.entity.user.Customer;
import coworkingApp.entity.user.User;
import coworkingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addUser(String name, String surname, String email, String userType) {
        User user;
        if ("Admin".equalsIgnoreCase(userType)) {
            user = new Admin(name, surname, email);
        } else if ("Customer".equalsIgnoreCase(userType)) {
            user = new Customer(name, surname, email);
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
