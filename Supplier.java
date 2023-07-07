import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Supplier extends JFrame {
    private JLabel supplierIDLabel, supplierNameLabel, contactNameLabel, emailLabel, phoneLabel;
    private JTextField supplierIDField, supplierNameField, contactNameField, emailField, phoneField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Supplier() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        supplierIDLabel = new JLabel("Supplier ID:");
        supplierNameLabel = new JLabel("Supplier Name:");
        contactNameLabel = new JLabel("Contact Name:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");

        supplierIDField = new JTextField(10);
        supplierNameField = new JTextField(10);
        contactNameField = new JTextField(10);
        emailField = new JTextField(10);
        phoneField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertSupplier();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifySupplier();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displaySuppliers();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(supplierIDLabel, c);

        c.gridx = 1;
        add(supplierIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(supplierNameLabel, c);

        c.gridx = 1;
        add(supplierNameField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(contactNameLabel, c);

        c.gridx = 1;
        add(contactNameField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(emailLabel, c);

        c.gridx = 1;
        add(emailField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(phoneLabel, c);

        c.gridx = 1;
        add(phoneField, c);

        c.gridx = 0;
        c.gridy = 5;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 6;
        add(deleteButton, c);

        c.gridx = 1;
        add(displayButton, c);
        addWindowListener(new WindowAdapter() {
            @SuppressWarnings("deprecation")
			@Override
            public void windowClosing(WindowEvent e) {
                // Handle the window closing event
                dispose();
                //HomePage hp = new HomePage();
                //hp.show();
            }
        });

        setTitle("Smart Buffer Stock Solution");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","harshitha","harshitha");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertSupplier() {
        try {
            String query = "INSERT INTO supplier (supplierID, supplierName, contactName, email, phone) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierIDField.getText());
            preparedStatement.setString(2, supplierNameField.getText());
            preparedStatement.setString(3, contactNameField.getText());
            preparedStatement.setString(4, emailField.getText());
            preparedStatement.setString(5, phoneField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifySupplier() {
        try {
            String query = "UPDATE supplier SET supplierName=?, contactName=?, email=?, phone=? " +
                    "WHERE supplierID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierNameField.getText());
            preparedStatement.setString(2, contactNameField.getText());
            preparedStatement.setString(3, emailField.getText());
            preparedStatement.setString(4, phoneField.getText());
            preparedStatement.setString(5, supplierIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSupplier() {
        try {
            String query = "DELETE FROM supplier WHERE supplierID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySuppliers() {
        try {
            String query = "SELECT * FROM supplier";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String supplierID = resultSet.getString("supplierID");
                String supplierName = resultSet.getString("supplierName");
                String contactName = resultSet.getString("contactName");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                System.out.println("Supplier ID: " + supplierID);
                System.out.println("Supplier Name: " + supplierName);
                System.out.println("Contact Name: " + contactName);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("---------------------------");
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Supplier();
            }
        });
    }
}

