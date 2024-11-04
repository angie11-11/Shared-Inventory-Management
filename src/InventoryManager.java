import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryManager {
    private int inventory = 0;                   // Current inventory count
    private final int MAX_INVENTORY;             // Maximum storage capacity
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public InventoryManager(int maxInventory) {
        this.MAX_INVENTORY = maxInventory;
    }

    // Utility method to get a timestamp
    private String getTimestamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    // Supplier method to add items to the inventory
    public void addItem(int quantity, String threadName) throws InterruptedException {
        lock.lock();
        try {
            while (inventory + quantity > MAX_INVENTORY) {
                System.out.printf("[%s] %s: Inventory full. Supplier waiting for space...\n", getTimestamp(), threadName);
                notFull.await();  // Wait until there's space to add items
            }
            inventory += quantity;
            System.out.printf("[%s] %s: Added %d items. Current Inventory: %d\n", getTimestamp(), threadName, quantity, inventory);
            notEmpty.signalAll();  // Signal customers waiting for stock
        } finally {
            lock.unlock();
        }
    }

    // Customer method to remove items from the inventory
    public void removeItem(int quantity, String threadName) throws InterruptedException {
        lock.lock();
        try {
            while (inventory < quantity) {
                System.out.printf("[%s] %s: Inventory empty. Customers waiting for items...\n", getTimestamp(), threadName);
                notEmpty.await();  // Wait until there are enough items to remove
            }
            inventory -= quantity;
            System.out.printf("[%s] %s: Removed %d items. Current Inventory: %d\n", getTimestamp(), threadName, quantity, inventory);
            notFull.signalAll();  // Signal suppliers waiting for space
        } finally {
            lock.unlock();
        }
    }
}
