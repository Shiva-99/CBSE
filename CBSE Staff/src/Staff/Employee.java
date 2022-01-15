package Staff;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Employee extends Staff {

	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	Staff staffBean = (Staff) context.getBean("staffbean");
	
    public Employee() {
        super();
    }
    public Employee(int newID, String newLastName, String newFirstName, String newPassward) {
        super(newID, newLastName, newFirstName, newPassward);
    }

}