import java.util.List;

/**
 * Class representing an admin, inheriting from Person.
 */
public class Admin extends Person {
    private List<Product> productList;

    public Admin(String name, String email, String address, String phoneNumber, List<Product> productList) {
        super(name, email, address, phoneNumber);
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }


}
