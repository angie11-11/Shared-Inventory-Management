class Customers extends Thread {
    private final InventoryManager manager;
    private final int quantity;
    private final int customerId; // Unique ID for the customer

    public Customers(InventoryManager manager, int quantity, int customerId) {
        this.manager = manager;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                manager.removeItem(quantity, "CustomerID: " + customerId);
                Thread.sleep(1500); // Simulating delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
