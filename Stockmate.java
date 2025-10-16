import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Stockmate {

    public static void main(String[] args) {
        DBHelper.createTable(); // ensure DB exists
        SwingUtilities.invokeLater(() -> new Stockmate().createWelcomeWindow());
    }

    // ================= Welcome Window =================
    private void createWelcomeWindow() {
        JFrame frame = new JFrame("Stockmate - Digital Inventory System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Stockmate", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setForeground(new Color(25, 25, 112));
        mainPanel.add(title, gbc);

        JLabel tagline = new JLabel("A Digital Inventory System for Efficient Workshop Stock Management.");
        tagline.setFont(new Font("Arial", Font.ITALIC, 18));
        tagline.setForeground(new Color(70, 70, 70));
        mainPanel.add(tagline, gbc);

        JButton adminButton = new JButton("Admin Login");
        JButton userButton = new JButton("User Login");
        Dimension buttonSize = new Dimension(220, 50);
        adminButton.setPreferredSize(buttonSize);
        userButton.setPreferredSize(buttonSize);

        styleButton(adminButton, new Color(70, 130, 180), new Color(100, 149, 237));
        styleButton(userButton, new Color(34, 139, 34), new Color(50, 205, 50));

        mainPanel.add(adminButton, gbc);
        mainPanel.add(userButton, gbc);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        adminButton.addActionListener(e -> openLoginWindow(frame, "Admin"));
        userButton.addActionListener(e -> openLoginWindow(frame, "User"));
    }

    // ================= Login Window =================

      private static void openLoginWindow(JFrame parent, String role) {
        JFrame loginFrame = new JFrame(role + " Login");
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel(role + " Login");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(25, 25, 112));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        panel.add(title, gbc);

        gbc.gridwidth=1; gbc.anchor=GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=1; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx=1; JTextField emailField = new JTextField(15); panel.add(emailField, gbc);

        gbc.gridx=0; gbc.gridy=2; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx=1; JPasswordField passwordField = new JPasswordField(15); panel.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(70, 130, 180), new Color(100, 149, 237));
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            if(role.equals("Admin")) {
                if(email.equals("risvananasrincv@gmail.com") && pass.equals("risvana1288")) {
                    JOptionPane.showMessageDialog(loginFrame,"Admin login successful!");
                    loginFrame.dispose(); openAdminDashboard(parent);
                } else JOptionPane.showMessageDialog(loginFrame,"Invalid Admin credentials!");
            } else {
                if(email.endsWith("@gmail.com")) {
                    JOptionPane.showMessageDialog(loginFrame,"User login successful!");
                    loginFrame.dispose(); openUserDashboard(parent);
                } else JOptionPane.showMessageDialog(loginFrame,"Invalid User email! Must be Gmail.");
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
    // ================= Admin Dashboard =================
    private static void openAdminDashboard(JFrame parent) {
        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(600, 400);
        adminFrame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,250,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.gridy=GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15,0,15,0); gbc.anchor=GridBagConstraints.CENTER;

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Serif", Font.BOLD, 32)); title.setForeground(new Color(25,25,112));
        panel.add(title, gbc);

        JButton addItemBtn = new JButton("Add Item");
        JButton updateItemBtn = new JButton("Update Item");
        JButton searchItemBtn = new JButton("Search Item");
        JButton viewAllBtn = new JButton("View All Items");
        JButton deleteItemBtn = new JButton("Delete Item"); // new delete button

        Dimension btnSize = new Dimension(200,45);
        addItemBtn.setPreferredSize(btnSize); updateItemBtn.setPreferredSize(btnSize);
        searchItemBtn.setPreferredSize(btnSize); viewAllBtn.setPreferredSize(btnSize);
        deleteItemBtn.setPreferredSize(btnSize);

        styleButton(addItemBtn,new Color(70,130,180), new Color(100,149,237));
        styleButton(updateItemBtn,new Color(34,139,34), new Color(50,205,50));
        styleButton(searchItemBtn,new Color(255,140,0), new Color(255,165,0));
        styleButton(viewAllBtn,new Color(128,0,128), new Color(186,85,211));
        styleButton(deleteItemBtn,new Color(178,34,34), new Color(220,20,60)); // red delete

        panel.add(addItemBtn,gbc); panel.add(updateItemBtn,gbc);
        panel.add(searchItemBtn,gbc); panel.add(viewAllBtn,gbc); panel.add(deleteItemBtn,gbc);

        addItemBtn.addActionListener(e -> openItemForm(adminFrame,"Add New Item"));
        updateItemBtn.addActionListener(e -> openUpdateItemForm(adminFrame));
        searchItemBtn.addActionListener(e -> openSearchItemWindow(adminFrame));
        viewAllBtn.addActionListener(e -> displayAllItems(adminFrame));
        deleteItemBtn.addActionListener(e -> deleteItem(adminFrame)); // delete action

        adminFrame.add(panel);
        adminFrame.setVisible(true);
    }

    // ================= User Dashboard =================
    private static void openUserDashboard(JFrame parent) {
        JFrame userFrame = new JFrame("User Dashboard");
        userFrame.setSize(400,300);
        userFrame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,250,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.gridy=GridBagConstraints.RELATIVE;
        gbc.insets=new Insets(15,0,15,0); gbc.anchor=GridBagConstraints.CENTER;

        JLabel title = new JLabel("User Dashboard");
        title.setFont(new Font("Serif", Font.BOLD,32)); title.setForeground(new Color(25,25,112));
        panel.add(title,gbc);

        JButton searchBtn = new JButton("View/Search Item");
        styleButton(searchBtn,new Color(255,140,0), new Color(255,165,0));
        searchBtn.setPreferredSize(new Dimension(200,45));
        panel.add(searchBtn,gbc);
        searchBtn.addActionListener(e -> openSearchItemWindow(userFrame));

        JButton viewAllBtn = new JButton("View All Items");
        styleButton(viewAllBtn,new Color(128,0,128), new Color(186,85,211));
        viewAllBtn.setPreferredSize(new Dimension(200,45));
        panel.add(viewAllBtn,gbc);
        viewAllBtn.addActionListener(e -> displayAllItems(userFrame));

        userFrame.add(panel); userFrame.setVisible(true);
    }

    // ================= Add Item Form =================
    private static void openItemForm(JFrame parent, String titleText) {
        JFrame frame = new JFrame(titleText);
        frame.setSize(500,400); frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,250,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10); gbc.anchor=GridBagConstraints.WEST;

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Serif", Font.BOLD,28)); title.setForeground(new Color(25,25,112));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; panel.add(title,gbc);

        String[] labels = {"Item ID:","Item Name:","Category:","Subcategory:","Quantity:","Box Number:"};
        JTextField[] fields = new JTextField[labels.length];
        gbc.gridwidth=1;

        for(int i=0;i<labels.length;i++){
            gbc.gridx=0; gbc.gridy=i+1; panel.add(new JLabel(labels[i]),gbc);
            gbc.gridx=1; fields[i] = new JTextField(15); panel.add(fields[i],gbc);
        }

        JButton actionBtn = new JButton(titleText); styleButton(actionBtn,new Color(70,130,180), new Color(100,149,237));
        gbc.gridx=0; gbc.gridy=labels.length+1; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        panel.add(actionBtn,gbc);

        actionBtn.addActionListener(e -> {
            try {
                String itemId = fields[0].getText().trim();
                String name = fields[1].getText().trim();
                String category = fields[2].getText().trim();
                String subcategory = fields[3].getText().trim();
                int quantity = Integer.parseInt(fields[4].getText().trim());
                String box = fields[5].getText().trim();

                if(titleText.equals("Add New Item")){
                    if(DBHelper.addItem(itemId,name,category,subcategory,quantity,box))
                        JOptionPane.showMessageDialog(panel,"Item added successfully!");
                    else JOptionPane.showMessageDialog(panel,"Error adding item. Maybe ID exists.");
                }
            } catch(NumberFormatException ex){ JOptionPane.showMessageDialog(panel,"Quantity must be a number!"); }
        });

        frame.add(panel); frame.setVisible(true);
    }

    // ================= Update Item Form =================
    private static void openUpdateItemForm(JFrame parent) {
        String itemId = JOptionPane.showInputDialog(parent, "Enter Item ID to update:");
        if(itemId == null || itemId.trim().isEmpty()) return;

        String[] item = DBHelper.getItemById(itemId.trim());
        if(item == null){
            JOptionPane.showMessageDialog(parent, "Item ID not found!");
            return;
        }

        JFrame frame = new JFrame("Update Item");
        frame.setSize(500, 400); frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,250,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10); gbc.anchor=GridBagConstraints.WEST;

        JLabel title = new JLabel("Update Item");
        title.setFont(new Font("Serif", Font.BOLD,28)); title.setForeground(new Color(25,25,112));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; panel.add(title,gbc);

        String[] labels = {"Item ID:","Item Name:","Category:","Subcategory:","Quantity:","Box Number:"};
        JTextField[] fields = new JTextField[labels.length];
        gbc.gridwidth=1;

        for(int i=0;i<labels.length;i++){
            gbc.gridx=0; gbc.gridy=i+1; panel.add(new JLabel(labels[i]),gbc);
            gbc.gridx=1; fields[i] = new JTextField(15); panel.add(fields[i],gbc);
        }

        for(int i=0;i<item.length;i++) fields[i].setText(item[i]);
        fields[0].setEditable(false);

        JButton updateBtn = new JButton("Update Item");
        styleButton(updateBtn,new Color(34,139,34), new Color(50,205,50));
        gbc.gridx=0; gbc.gridy=labels.length+1; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        panel.add(updateBtn,gbc);

        updateBtn.addActionListener(e -> {
            try {
                String name = fields[1].getText().trim();
                String category = fields[2].getText().trim();
                String subcategory = fields[3].getText().trim();
                int quantity = Integer.parseInt(fields[4].getText().trim());
                String box = fields[5].getText().trim();

                if(DBHelper.updateItem(itemId,name,category,subcategory,quantity,box))
                    JOptionPane.showMessageDialog(panel,"Item updated successfully!");
                else
                    JOptionPane.showMessageDialog(panel,"Error updating item!");
            } catch(NumberFormatException ex){ 
                JOptionPane.showMessageDialog(panel,"Quantity must be a number!"); 
            }
        });

        frame.add(panel); frame.setVisible(true);
    }

    // ================= Delete Item =================
    private static void deleteItem(JFrame parent){
        String itemId = JOptionPane.showInputDialog(parent,"Enter Item ID to delete:");
        if(itemId==null || itemId.trim().isEmpty()) return;
        boolean success = DBHelper.deleteItem(itemId.trim());
        if(success) JOptionPane.showMessageDialog(parent,"Item deleted successfully!");
        else JOptionPane.showMessageDialog(parent,"Item ID not found!");
    }

    // ================= Search Item =================
    private static void openSearchItemWindow(JFrame parent){
        JFrame frame = new JFrame("Search Item");
        frame.setSize(400,250); frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,250,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10); gbc.anchor=GridBagConstraints.WEST;

        JLabel title = new JLabel("Search Item");
        title.setFont(new Font("Serif", Font.BOLD,28)); title.setForeground(new Color(25,25,112));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; panel.add(title,gbc);

        gbc.gridwidth=1; gbc.gridx=0; gbc.gridy=1; panel.add(new JLabel("Item ID:"),gbc);
        gbc.gridx=1; JTextField itemField = new JTextField(15); panel.add(itemField,gbc);

        JButton searchBtn = new JButton("Search"); styleButton(searchBtn,new Color(255,140,0), new Color(255,165,0));
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER; panel.add(searchBtn,gbc);

        searchBtn.addActionListener(e -> {
            String id = itemField.getText().trim();
            if(!id.isEmpty()){
                String[] item = DBHelper.getItemById(id);
                if(item!=null){
                    JOptionPane.showMessageDialog(frame,
                            "Item ID: "+item[0]+"\nName: "+item[1]+"\nCategory: "+item[2]+
                                    "\nSubcategory: "+item[3]+"\nQuantity: "+item[4]+"\nBox No: "+item[5]);
                } else JOptionPane.showMessageDialog(frame,"Item not found!");
            } else JOptionPane.showMessageDialog(frame,"Enter Item ID!");
        });

        frame.add(panel); frame.setVisible(true);
    }

    // ================= View All Items =================
  private static void displayAllItems(JFrame parent){
        JFrame frame = new JFrame("All Items");
        frame.setSize(700,400); frame.setLocationRelativeTo(parent);

        String[] columns = {"Item ID","Name","Category","Subcategory","Quantity","Box No"};
        DefaultTableModel model = new DefaultTableModel(columns,0);
        JTable table = new JTable(model);

        List<String[]> items = DBHelper.getAllItems();
        for(String[] row: items) model.addRow(row);

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
            }

    // ================= Button Styling =================
   








