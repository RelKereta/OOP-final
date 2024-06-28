import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class representing the GUI for the e-commerce system.
 */
public class ECommerceGUI extends JFrame {
    private DefaultListModel<Product> productListModel;
    private DefaultListModel<Product> cartListModel;
    private JList<Product> productList;
    private JList<Product> cartList;
    private ShoppingCart cart;
    private JLabel totalPriceLabel;
    private Person currentUser;

    /**
     * Constructor for ECommerceGUI for a User.
     *
     * @param user the current user
     */
    public ECommerceGUI(User user) {
        this.currentUser = user;
        this.cart = user.getCart();
        initialize();
    }

    /**
     * Constructor for ECommerceGUI for an Admin.
     *
     * @param admin the current admin
     */
    public ECommerceGUI(Admin admin) {
        this.currentUser = admin;
        this.cart = new ShoppingCart();
        initialize(admin.getProductList());
    }

    /**
     * Initializes the GUI components.
     *
     * @param products the list of products, only used for admin
     */
    private void initialize(java.util.List<Product>... products) {
        productListModel = new DefaultListModel<>();
        cartListModel = new DefaultListModel<>();

        // Populate product list model for admin
        if (products.length > 0) {
            for (Product product : products[0]) {
                productListModel.addElement(product);
            }
        }

        // Add some sample products for regular user
        if (products.length == 0) {
            productListModel.addElement(new Product("Laptop", 999.99, 5));
            productListModel.addElement(new Product("Smartphone", 499.99, 10));
            productListModel.addElement(new Product("Headphones", 49.99, 15));
            productListModel.addElement(new Product("Keyboard", 29.99, 30));
        }

        setTitle("Simple E-Commerce System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents(); // Initialize GUI components

        pack(); // Pack components to fit preferred sizes
        setLocationRelativeTo(null); // Center frame on screen
    }

    /**
     * Initializes the GUI components.
     */
    private void initComponents() {
        // Product list panel
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        productList = new JList<>(productListModel);
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        // Cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        cartList = new JList<>(cartListModel);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Product selectedProduct = productList.getSelectedValue();
                    if (selectedProduct != null && selectedProduct.getQuantity() > 0) {
                        selectedProduct.decreaseQuantity();
                        cart.addProduct(selectedProduct);
                        cartListModel.addElement(selectedProduct);
                        updateTotalPrice();
                        productList.repaint(); // Refresh product list to show updated stock
                    } else {
                        throw new InvalidProductException("No product selected or product out of stock.");
                    }
                } catch (InvalidProductException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton removeButton = new JButton("Remove from Cart");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Product selectedProduct = cartList.getSelectedValue();
                    if (selectedProduct != null) {
                        selectedProduct.increaseQuantity();
                        cart.removeProduct(selectedProduct);
                        cartListModel.removeElement(selectedProduct);
                        updateTotalPrice();
                        productList.repaint(); // Refresh product list to show updated stock
                    } else {
                        throw new InvalidProductException("No product selected to remove from cart.");
                    }
                } catch (InvalidProductException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double totalPrice = cart.getTotalPrice();
                JOptionPane.showMessageDialog(null, "Total Price: $" + totalPrice);
                cart.clearCart();
                cartListModel.clear();
                updateTotalPrice();
                productList.repaint(); // Refresh product list to show updated stock
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);

        // Total price label
        totalPriceLabel = new JLabel("Total Price: $0.00");

        // Add components to the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(productPanel, BorderLayout.WEST);
        contentPane.add(cartPanel, BorderLayout.EAST);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(totalPriceLabel, BorderLayout.NORTH);
    }

    /**
     * Updates the total price label with the current total price of the cart.
     */
    private void updateTotalPrice() {
        double totalPrice = cart.getTotalPrice();
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
    }

    /**
     * Custom exception class for invalid product operations.
     */
    class InvalidProductException extends Exception {
        public InvalidProductException(String message) {
            super(message);
        }
    }
}
