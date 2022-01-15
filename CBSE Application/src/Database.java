import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Comparator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Base.MenuItem;
import Order.Order;
import Staff.Employee;
import Staff.Manager;
import Staff.Staff;

public class Database {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	MenuItem menuItemBean = (MenuItem) context.getBean("menuitembean");
	Order orderBean = (Order) context.getBean("orderbean");
	Staff staffBean = (Staff) context.getBean("staffbean");

	
    public final static String STAFF_FILE = "dataFiles/staff.txt";
    public final static String MANAGER_FILE = "dataFiles/manager.txt";
    public final static String MENU_FILE = "dataFiles/menu_item.txt";

    public ArrayList < Staff > staffList = new ArrayList < Staff > ();
    public ArrayList < MenuItem > menuList = new ArrayList < MenuItem > ();
    public ArrayList < Order > orderList = new ArrayList < Order > ();

    public Date date;
    public int todaysOrderCounts;
    /****************************************************************************
     * Constructor
     ***************************************************************************/
    public Database() {
        date = new Date();
        todaysOrderCounts = 0; //Load order file??
    }
    /****************************************************************************
     * Getter
     ***************************************************************************/
    public ArrayList < Staff > getStaffList() {
        return staffList;
    }

    public ArrayList < MenuItem > getMenuList() {
        return menuList;
    }

    public ArrayList < Order > getOrderList() {
        return orderList;
    }

    public int getTodaysOrderCount() {
        return this.todaysOrderCounts;
    }

    //----------------------------------------------------------
    // Find staff from ID
    //----------------------------------------------------------
    public Staff findStaffByID(int id) {
        Iterator < Staff > it = staffList.iterator();
        Staff re = null;
        boolean found = false;

        if (id < 0) {
            return null;
        }

        while (it.hasNext() && !found) {
            re = (Staff) it.next();
            if (re.getID() == id) {
                found = true;
            }
        }

        if (found)
            return re;
        else
            return null;
    }

    //----------------------------------------------------------
    // Find menu item from ID
    //----------------------------------------------------------
    public MenuItem findMenuItemByID(int id) {
        Iterator < MenuItem > it = menuList.iterator();
        MenuItem re = null;
        boolean found = false;

        if (id < 0) {
            return null;
        }

        while (it.hasNext() && !found) {
            re = (MenuItem) it.next();
            if (re.getID() == id) {
                found = true;
            }
        }

        if (found)
            return re;
        else
            return null;
    }

    //----------------------------------------------------------
    // Find order from ID
    //----------------------------------------------------------
    public Order findOrderByID(int id) {
        Iterator < Order > it = orderList.iterator();
        Order re = null;
        boolean found = false;

        if (id < 0) {
            return null;
        }

        while (it.hasNext() && !found) {
            re = it.next();
            if (re.getOrderID() == id) {
                found = true;
            }
        }

        if (found)
            return re;
        else
            return null;
    }


    /****************************************************************************
     * Manipulate data
     ***************************************************************************/

    //---------------------------------------------------------------
    // Order
    //---------------------------------------------------------------
    public int addOrder(int staffID, String staffName) {
        int newOrderID = ++todaysOrderCounts;
        Order newOrder = new Order(staffID, staffName);
        newOrder.setOrderID(newOrderID);
        orderList.add(newOrder);
        return newOrderID;
    }

    public void addOrderItem(int orderID, MenuItem rItem, byte quantity) {
        Order rOrder = findOrderByID(orderID);
        rOrder.addItem(rItem, quantity);
    }

    public boolean deleteOrderItem(int orderID, int index) {
        Order rOrder = findOrderByID(orderID);
        if (rOrder == null)
            return false;
        return rOrder.deleteItem(index);
    }


    //Cancel order: order data is not deleted from the database(Just put cancel flag on)
    public boolean cancelOrder(int orderID) {
        Order rOrder = findOrderByID(orderID);
        if (rOrder == null)
            return false;
        rOrder.setState(Order.ORDER_CANCELED);
        return true;
    }
    //Delete order: order data is deleted from the database
    public boolean deleteOrder(int orderID) {
        Order rOrder = findOrderByID(orderID);
        if (rOrder == null)
            return false;
        orderList.remove(rOrder);
        todaysOrderCounts--;
        return true;
    }

    public boolean closeOrder(int orderID) {
        Order rOrder = findOrderByID(orderID);
        if (rOrder == null)
            return false;
        rOrder.setState(Order.ORDER_CLOSED);
        return true;
    }


    public int getOrderState(int orderID) {
        Order re = findOrderByID(orderID);
        if (re == null)
            return -1;
        return re.getState();
    }

    public double getOrderTotalCharge(int orderID) {
        Order re = findOrderByID(orderID);
        if (re == null)
            return -1;
        return re.getTotal();
    }


    /****************************************************************************
     * File load
     ***************************************************************************/
    public void loadFiles() {
        loadStaffFile();
        loadManagerFile();
        Collections.sort(staffList, new StaffComparator());
        loadMenuFile();
    }

    public void loadStaffFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(STAFF_FILE));
            String line = reader.readLine();

            while (line != null) {
                String[] record = line.split(",");

                String id = record[0].trim();
                String passward = record[1].trim();
                String firstName = record[2].trim();
                String lastName = record[3].trim();

                // Add the data from file to the registerCourses array list
                Employee rEmployee = new Employee(Integer.parseInt(id), lastName, firstName, passward);
                staffList.add(rEmployee);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ioe) {}
    }

    public void loadManagerFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MANAGER_FILE));
            String line = reader.readLine();

            while (line != null) {
                String[] record = line.split(",");

                String id = record[0].trim();
                String passward = record[1].trim();
                String firstName = record[2].trim();
                String lastName = record[3].trim();

                // Add the data from file to the registerCourses array list
                Manager rManager = new Manager(Integer.parseInt(id), lastName, firstName, passward);
                staffList.add(rManager);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ioe) {}
    }

    public void loadMenuFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE));
            String line = reader.readLine();

            while (line != null) {
                String[] record = line.split(",");

                String id = record[0].trim();
                String name = record[1].trim();
                String price = record[2].trim();
                String type = record[3].trim();

                // Add the data from file to the registerCourses array list
                MenuItem rMenuItem = new MenuItem(Integer.parseInt(id), name, Double.parseDouble(price), Byte.parseByte(type));
                menuList.add(rMenuItem);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ioe) {}
    }

    /****************************************************************************
     * Comparator
     ***************************************************************************/
    public class StaffComparator implements Comparator < Staff > {

        @Override
        public int compare(Staff s1, Staff s2) {
            return s1.getID() < s2.getID() ? -1 : 1;
        }
    }

    public class MenuItemComparator implements Comparator < MenuItem > {

        @Override
        public int compare(MenuItem m1, MenuItem m2) {
            return m1.getID() < m2.getID() ? -1 : 1;
        }
    }
}