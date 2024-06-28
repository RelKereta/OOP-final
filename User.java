/**
 * Class representing a user, inheriting from Person.
 */
public class User extends Person {
    private ShoppingCart cart;

    public User(String name, String email, String address, String phoneNumber) {
        super(name, email, address, phoneNumber);
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
