import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class purchaseorder extends JFrame {
    private JLabel purchaseOrderIDLabel, supplierIDLabel, itemIDLabel, orderDateLabel, deliveryDateLabel, orderQuantityLabel;
    private JTextField purchaseOrderIDField, supplierIDField, itemIDField, orderDateField, deliveryDateField, orderQuantityField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public purchaseorder() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        purchaseOrderIDLabel = new JLabel("Purchase Order ID:");
        supplierIDLabel = new JLabel("Supplier ID:");
        itemIDLabel = new JLabel("Item ID:");
        orderDateLabel = new JLabel("Order Date:");
        deliveryDateLabel = new JLabel("Delivery Date:");
        orderQuantityLabel = new JLabel("order Quantity:");

        purchaseOrderIDField = new JTextField(10);
        supplierIDField = new JTextField(10);
        itemIDField = new JTextField(10);
        orderDateField = new JTextField(10);
        deliveryDateField = new JTextField(10);
        orderQuantityField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertPurchaseOrder();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyPurchaseOrder();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePurchaseOrder();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayPurchaseOrders();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(purchaseOrderIDLabel, c);

        c.gridx = 1;
        add(purchaseOrderIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(supplierIDLabel, c);

        c.gridx = 1;
        add(supplierIDField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(itemIDLabel, c);

        c.gridx = 1;
        add(itemIDField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(orderDateLabel, c);

        c.gridx = 1;
        add(orderDateField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(deliveryDateLabel, c);

        c.gridx = 1;
        add(deliveryDateField, c);

        c.gridx = 0;
        c.gridy = 5;
        add(orderQuantityLabel, c);

        c.gridx = 1;
        add(orderQuantityField, c);

        c.gridx = 0;
        c.gridy = 6;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 7;
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
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private void insertPurchaseOrder() {
        try {
            String query = "INSERT INTO purchaseorder (purchaseOrderID, supplierID, itemID, orderDate, deliveryDate, orderQuantity) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchaseOrderIDField.getText());
            preparedStatement.setString(2, supplierIDField.getText());
            preparedStatement.setString(3, itemIDField.getText());
            preparedStatement.setString(4, orderDateField.getText());
            preparedStatement.setString(5, deliveryDateField.getText());
            preparedStatement.setInt(6, Integer.parseInt(orderQuantityField.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Purchase order inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyPurchaseOrder() {
        try {
            String query = "UPDATE purchaseorder SET supplierID=?, itemID=?, orderDate=?, deliveryDate=?, orderQuantity=? " +
                    "WHERE purchaseOrderID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierIDField.getText());
            preparedStatement.setString(2, itemIDField.getText());
            preparedStatement.setString(3, orderDateField.getText());
            preparedStatement.setString(4, deliveryDateField.getText());
            preparedStatement.setInt(5, Integer.parseInt(orderQuantityField.getText()));
            preparedStatement.setString(6, purchaseOrderIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Purchase order modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletePurchaseOrder() {
        try {
            String query = "DELETE FROM purchaseorder WHERE purchaseOrderID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchaseOrderIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Purchase order deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPurchaseOrders() {
        try {
            String query = "SELECT * FROM purchaseorder";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String purchaseOrderID = resultSet.getString("purchaseOrderID");
                String supplierID = resultSet.getString("supplierID");
                String itemID = resultSet.getString("itemID");
                String orderDate = resultSet.getString("orderDate");
                String deliveryDate = resultSet.getString("deliveryDate");
                int deliveryQuantity = resultSet.getInt("orderQuantity");

                System.out.println("Purchase Order ID: " + purchaseOrderID);
                System.out.println("Supplier ID: " + supplierID);
                System.out.println("Item ID: " + itemID);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Delivery Date: " + deliveryDate);
                System.out.println("order Quantity: " + deliveryQuantity);
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
                new purchaseorder();
            }
        });
    }
}
