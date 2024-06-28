import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Main class to handle login functionality and launch the appropriate GUI based on user roles.
 */
public class ECommerceMain {
    private static Map<String, String> userCredentials;
    private static Map<String, Person> users;

    public static void main(String[] args) {
        // Predefined users
        userCredentials = new HashMap<>();
        userCredentials.put("admin", "admin123");
        userCredentials.put("user1", "user123");

        // Predefined user objects
        users = new HashMap<>();
        Admin admin = new Admin("Admin", "admin@example.com", "123 Admin St", "123-456-7890", Collections.emptyList());
        User user1 = new User("User One", "user1@example.com", "123 User St", "123-456-7891");

        users.put("admin", admin);
        users.put("user1", user1);

        // Launch login GUI
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }

    /**
     * Class representing the Login GUI.
     */
    static class LoginGUI extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        /**
         * Constructor for LoginGUI.
         */
        public LoginGUI() {
            setTitle("Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 150);
            setLocationRelativeTo(null);

            // Initialize GUI components
            initComponents();
        }

        /**
         * Initializes the GUI components for the login screen.
         */
        private void initComponents() {
            JPanel panel = new JPanel(new GridLayout(3, 2));

            // Username label and text field
            panel.add(new JLabel("Username:"));
            usernameField = new JTextField();
            panel.add(usernameField);

            // Password label and password field
            panel.add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            panel.add(passwordField);

            // Login button
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new LoginButtonListener());
            panel.add(loginButton);

            // Add panel to content pane
            getContentPane().add(panel, BorderLayout.CENTER);
        }

        /**
         * ActionListener for the login button.
         */
        private class LoginButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(username, password)) {
                    Person user = users.get(username);
                    if (user instanceof Admin) {
                        SwingUtilities.invokeLater(() -> new ECommerceGUI((Admin) user).setVisible(true));
                    } else {
                        SwingUtilities.invokeLater(() -> new ECommerceGUI((User) user).setVisible(true));
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }

            /**
             * Authenticates the user based on username and password.
             *
             * @param username the username
             * @param password the password
             * @return true if authentication is successful, false otherwise
             */
            private boolean authenticate(String username, String password) {
                return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
            }
        }
    }
}
