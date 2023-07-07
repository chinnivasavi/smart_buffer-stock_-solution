import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Sales extends JFrame {
    private JLabel salesIDLabel, itemIDLabel, salesDateLabel, salesQuantityLabel;
    private JTextField salesIDField, itemIDField, salesDateField, salesQuantityField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Sales() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        salesIDLabel = new JLabel("Sales ID:");
        itemIDLabel = new JLabel("Item ID:");
        salesDateLabel = new JLabel("Sales Date:");
        salesQuantityLabel = new JLabel("Sales Quantity:");

        salesIDField = new JTextField(10);
        itemIDField = new JTextField(10);
        salesDateField = new JTextField(10);
        salesQuantityField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertSales();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifySales();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSales();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displaySales();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(salesIDLabel, c);

        c.gridx = 1;
        add(salesIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(itemIDLabel, c);

        c.gridx = 1;
        add(itemIDField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(salesDateLabel, c);

        c.gridx = 1;
        add(salesDateField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(salesQuantityLabel, c);

        c.gridx = 1;
        add(salesQuantityField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 5;
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

    private void insertSales() {
        try {
            String query = "INSERT INTO sales (salesID, itemID, salesDate, salesQuantity) " +
                    "VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, salesIDField.getText());
            preparedStatement.setString(2, itemIDField.getText());
            preparedStatement.setString(3, salesDateField.getText());
            preparedStatement.setInt(4, Integer.parseInt(salesQuantityField.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sales inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifySales() {
        try {
            String query = "UPDATE sales SET itemID=?, salesDate=?, salesQuantity=? " +
                    "WHERE salesID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemIDField.getText());
            preparedStatement.setString(2, salesDateField.getText());
            preparedStatement.setInt(3, Integer.parseInt(salesQuantityField.getText()));
            preparedStatement.setString(4, salesIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sales modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSales() {
        try {
            String query = "DELETE FROM sales WHERE salesID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, salesIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sales deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySales() {
        try {
            String query = "SELECT * FROM sales";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String salesID = resultSet.getString("salesID");
                String itemID = resultSet.getString("itemID");
                String salesDate = resultSet.getString("salesDate");
                int salesQuantity = resultSet.getInt("salesQuantity");

                System.out.println("Sales ID: " + salesID);
                System.out.println("Item ID: " + itemID);
                System.out.println("Sales Date: " + salesDate);
                System.out.println("Sales Quantity: " + salesQuantity);
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
                new Sales();
            }
        });
    }
}
