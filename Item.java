import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Item extends JFrame {
    private JLabel itemIDLabel, itemNameLabel, reorderLevelLabel, reorderQuantityLabel, currentStockLevelLabel;
    private JTextField itemIDField, itemNameField, reorderLevelField, reorderQuantityField, currentStockLevelField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Item() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        itemIDLabel = new JLabel("Item ID:");
        itemNameLabel = new JLabel("Item Name:");
        reorderLevelLabel = new JLabel("Reorder Level:");
        reorderQuantityLabel = new JLabel("Reorder Quantity:");
        currentStockLevelLabel = new JLabel("Current Stock Level:");

        itemIDField = new JTextField(10);
        itemNameField = new JTextField(10);
        reorderLevelField = new JTextField(10);
        reorderQuantityField = new JTextField(10);
        currentStockLevelField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertItem();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayItems();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(itemIDLabel, c);

        c.gridx = 1;
        add(itemIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(itemNameLabel, c);

        c.gridx = 1;
        add(itemNameField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(reorderLevelLabel, c);

        c.gridx = 1;
        add(reorderLevelField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(reorderQuantityLabel, c);

        c.gridx = 1;
        add(reorderQuantityField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(currentStockLevelLabel, c);

        c.gridx = 1;
        add(currentStockLevelField, c);

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

    private void insertItem() {
        try {
            String query = "INSERT INTO item (itemID, itemName, reorderLevel, reorderQuantity, currentStockLevel) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemIDField.getText());
            preparedStatement.setString(2, itemNameField.getText());
            preparedStatement.setInt(3, Integer.parseInt(reorderLevelField.getText()));
            preparedStatement.setInt(4, Integer.parseInt(reorderQuantityField.getText()));
            preparedStatement.setInt(5, Integer.parseInt(currentStockLevelField.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyItem() {
        try {
            String query = "UPDATE item SET itemName=?, reorderLevel=?, reorderQuantity=?, currentStockLevel=? " +
                    "WHERE itemID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemNameField.getText());
            preparedStatement.setInt(2, Integer.parseInt(reorderLevelField.getText()));
            preparedStatement.setInt(3, Integer.parseInt(reorderQuantityField.getText()));
            preparedStatement.setInt(4, Integer.parseInt(currentStockLevelField.getText()));
            preparedStatement.setString(5, itemIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteItem() {
        try {
            String query = "DELETE FROM item WHERE itemID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayItems() {
        try {
            String query = "SELECT * FROM item";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String itemID = resultSet.getString("itemID");
                String itemName = resultSet.getString("itemName");
                int reorderLevel = resultSet.getInt("reorderLevel");
                int reorderQuantity = resultSet.getInt("reorderQuantity");
                int currentStockLevel = resultSet.getInt("currentStockLevel");

                System.out.println("Item ID: " + itemID);
                System.out.println("Item Name: " + itemName);
                System.out.println("Reorder Level: " + reorderLevel);
                System.out.println("Reorder Quantity: " + reorderQuantity);
                System.out.println("Current Stock Level: " + currentStockLevel);
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
                new Item();
            }
        });
    }
}
