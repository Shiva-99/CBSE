package Staff;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Manager extends Staff {

	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	Staff staffBean = (Staff) context.getBean("staffbean");

    public Manager() {
        super();
    }
    public Manager(int newID, String newLastName, String newFirstName, String newPassward) {
        super(newID, newLastName, newFirstName, newPassward);
    }

}