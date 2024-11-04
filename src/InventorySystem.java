public class InventorySystem {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager(20); // Set max inventory limit to 20

        // Create supplier and customer threads with unique IDs and varied quantities
        Suppliers supplier1 = new Suppliers(manager, 5, 1);
        Suppliers supplier2 = new Suppliers(manager, 7, 2);

        Customers customer1 = new Customers(manager, 3, 1);
        Customers customer2 = new Customers(manager, 6, 2);
        Customers customer3 = new Customers(manager, 4, 3);

        // Start the threads
        supplier1.start();
        supplier2.start();
        customer1.start();
        customer2.start();
        customer3.start();
    }
}
