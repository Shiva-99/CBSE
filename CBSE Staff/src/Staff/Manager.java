package Staff;

import org.springframework.stereotype.Component;

@Component
public class Manager extends Staff {
    public Manager(int newID, String newLastName, String newFirstName, String newPassward) {
        super(newID, newLastName, newFirstName, newPassward);
    }
}