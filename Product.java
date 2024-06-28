/**
* Class representing a product.
*/
public class Product {
   private String name;
   private double price;
   private int quantity;

   public Product(String name, double price, int quantity) {
       this.name = name;
       this.price = price;
       this.quantity = quantity;
   }

   // Getters and setters
   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public double getPrice() {
       return price;
   }

   public void setPrice(double price) {
       this.price = price;
   }

   public int getQuantity() {
       return quantity;
   }

   public void setQuantity(int quantity) {
       this.quantity = quantity;
   }

   public void decreaseQuantity() {
       if (quantity > 0) {
           quantity--;
       }
   }

   public void increaseQuantity() {
       quantity++;
   }

   @Override
   public String toString() {
       return name + " - $" + price + " (" + quantity + " in stock)";
   }
}

