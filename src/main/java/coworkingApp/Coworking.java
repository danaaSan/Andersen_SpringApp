package coworkingApp;

import coworkingApp.service.BookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Coworking {

    public static void main(String[] args)  {
        SpringApplication.run(Coworking.class,args);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode("admin123");
        System.out.println("Зашифрованный пароль: " + encryptedPassword);

    }


}
