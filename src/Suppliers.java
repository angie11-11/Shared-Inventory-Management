class Suppliers extends Thread {
    private final InventoryManager manager;
    private final int quantity;
    private final int supplierId; // Unique ID for the supplier

    public Suppliers(InventoryManager manager, int quantity, int supplierId) {
        this.manager = manager;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                manager.addItem(quantity, "SupplierID: " + supplierId);
                Thread.sleep(1000); // Simulating delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
