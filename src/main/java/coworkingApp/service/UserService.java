package coworkingApp.service;

import coworkingApp.entity.User;
import coworkingApp.model.UserInputModel;
import coworkingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public User addUser(UserInputModel userInputModel) {
        User user = new User();
        user.setName(userInputModel.getName());
        user.setEmail(userInputModel.getEmail());
        user.setSurname(userInputModel.getSurname());
        user.setPassword(passwordEncoder.encode(userInputModel.getPassword()));
        user.setRole(User.Role.USER);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
