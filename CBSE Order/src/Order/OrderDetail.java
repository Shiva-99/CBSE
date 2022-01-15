package Order;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import Base.MenuItem;

@Component
public class OrderDetail {
    ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    MenuItem menuItemBeanNew = (MenuItem) context.getBean("menuitembean");
	
    public int itemID;
    public String itemName;
    public double price;
    public byte quantity;
    public double totalPrice;

    public OrderDetail() {
    
    }
    
    public OrderDetail(MenuItem newMenuItem, byte newQuantity) {
        this.itemID = newMenuItem.getID();
        this.itemName = newMenuItem.getName();
        this.price = newMenuItem.getPrice();
        this.quantity = newQuantity;
        this.totalPrice = this.price * this.quantity;
    }

    /**************************************************
     * Getter
     *************************************************/
    public int getItemID() {
        return this.itemID;
    }
    public String getItemName() {
        return this.itemName;
    }
    public double getPrice() {
        return this.price;
    }
    public byte getQuantity() {
        return this.quantity;
    }
    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void addQuantity(byte add) {
        quantity += add;
        totalPrice = price * quantity;
    }

}