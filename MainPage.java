import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JButton retrieveMarksButton;

    public MainPage() {
        // Set frame properties
        setTitle("Smart Buffer Stock Solution");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create label
        JLabel welcomeLabel = new JLabel("Welcome to Smart Buffer Stock Solution Database");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Create panel for the button
       // JPanel buttonPanel = new JPanel();
        //retrieveMarksButton = new JButton("Retrieve Marks");
       // buttonPanel.add(retrieveMarksButton);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu ItemMenu = new JMenu("Item Details");
        JMenu PurchaseOrderMenu = new JMenu("PurchaseOrder Details");
        JMenu SupplierMenu = new JMenu("Supplier Details");
        JMenu SalesMenu = new JMenu("Sales Details");
        

        // Create menu item for student menu
        JMenuItem viewItemDetails = new JMenuItem("View Item Details");
        viewItemDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Item();
            }
        });

        // Create menu item for course menu
        JMenuItem viewPurchaseOrderDetails = new JMenuItem("View PurchaseOrder Details");
        viewPurchaseOrderDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new purchaseorder();
            }
        });

        // Create menu item for enrollment menu
        JMenuItem viewSupplierDetails = new JMenuItem("View Supplier Details");
        viewSupplierDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Supplier();
            }
        });

        // Create menu item for semester menu
        JMenuItem viewSalesDetails = new JMenuItem("View Sales Details");
        viewSalesDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Sales();
            }
        });

        // Create menu item for grade menu
        JMenuItem viewGradeDetails = new JMenuItem("View Grade Details");
        viewGradeDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Sales();
            }
        });

        // Add menu items to respective menus
        ItemMenu.add(viewItemDetails);
        PurchaseOrderMenu.add(viewPurchaseOrderDetails);
        SupplierMenu.add(viewSupplierDetails);
        SalesMenu.add(viewSalesDetails);
        

        // Add menus to the menu bar
        menuBar.add(ItemMenu);
        menuBar.add(PurchaseOrderMenu);
        menuBar.add(SupplierMenu);
        menuBar.add(SalesMenu);
        

        // Set the menu bar
        setJMenuBar(menuBar);

        // Add the button panel to the frame
       // add(buttonPanel, BorderLayout.CENTER);

        // Set button action for "Retrieve Marks"
        //retrieveMarksButton.addActionListener(new ActionListener() {
          //  public void actionPerformed(ActionEvent e) {
            //    new Retreive();
            //}
        //});

        // Add window listener to handle maximizing the window
        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    System.out.println("Window maximized");
                } else {
                    System.out.println("Window not maximized");
                }
            }
        });

        // Set frame size and visibility
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPage();
    }
}