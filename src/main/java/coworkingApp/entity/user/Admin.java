package coworkingApp.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {
    public Admin() {}
    public Admin(String name, String surname, String email) {
        super(name, surname, email);
    }
}
