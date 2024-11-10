package Inventory;

public class InventoryItem {

	    private int id;
	    private String name;
	    private int quantity;
	    private double price;
	    

	    // Constructor
	    public InventoryItem(int id, String name, int quantity, double price) {
	        this.id = id;
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
	    }

	    // Getter and Setter for id
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    // Getter and Setter for name
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    // Getter and Setter for quantity
	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    // Getter and Setter for price
	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    // Method to update quantity
	    public void updateQuantity(int amount) {
	        this.quantity += amount;
	    }

	    // Method to calculate total value of item stock
	    public double calculateTotalValue() {
	        return this.quantity * this.price;
	    }
	    

	    // toString method for easy printing
	    @Override
	    public String toString() {
	        return "InventoryItem{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", quantity=" + quantity +
	                ", price=" + price +
	                ", totalValue=" + calculateTotalValue() +
	                '}';
	    }
	}

	

