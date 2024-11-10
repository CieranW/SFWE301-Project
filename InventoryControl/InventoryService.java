package Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<InventoryItem> inventory = new ArrayList<>();
    private int nextId = 1; // ID generator for new items

    // Add a new item to inventory
    public void addItem(String name, int quantity, double price) {
        InventoryItem item = new InventoryItem(nextId++, name, quantity, price);
        inventory.add(item);
    }

    // Retrieve all inventory items
    public List<InventoryItem> getAllItems() {
        return new ArrayList<>(inventory); // Return a copy of the list to protect original data
    }

    // Update an existing item by ID
    public boolean updateItem(int id, int quantity, double price) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                item.setQuantity(quantity);
                item.setPrice(price);
                return true; // Item found and updated
            }
        }
        return false; // Item not found
    }

    // Delete an item from inventory by ID
    public boolean deleteItem(int id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                inventory.remove(i);
                return true; // Item found and deleted
            }
        }
        return false; // Item not found
    }
}
