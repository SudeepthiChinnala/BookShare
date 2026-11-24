
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class BookDonationSystem extends JFrame {

    Color green = new Color(49, 85, 59);
    Color lightGreen = new Color(222, 234, 216);
    Color gold = new Color(190, 157, 82);
    Color cream = new Color(250, 246, 235);
    Color dark = new Color(55, 62, 48);

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<String[]> activities = new ArrayList<>();

    Student currentStudent;

    String adminId = "admin";
    String adminPassword = "admin123";

    // Student class
    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        String name, id, password, email, branch;

        Student(String name, String id, String password,
                String email, String branch) {
            this.name = name;
            this.id = id;
            this.password = password;
            this.email = email;
            this.branch = branch;
        }
    }

    // Book class
    static class Book implements Serializable {
        private static final long serialVersionUID = 1L;

        String title, author, donor;
        int quantity;

        Book(String title, String author, String donor, int quantity) {
            this.title = title;
            this.author = author;
            this.donor = donor;
            this.quantity = quantity;
        }
    }

    // Constructor
    public BookDonationSystem() {
        setTitle("Book Donation Management System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(cream);

        loadData();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveData();
                dispose();
            }
        });

        welcomeScreen();
        setVisible(true);
    }

    // Save all data into files
    void saveData() {
        try {
            ObjectOutputStream out;

            out = new ObjectOutputStream(
                new FileOutputStream("students.dat"));
            out.writeObject(students);
            out.close();

            out = new ObjectOutputStream(
                new FileOutputStream("books.dat"));
            out.writeObject(books);
            out.close();

            out = new ObjectOutputStream(
                new FileOutputStream("activities.dat"));
            out.writeObject(activities);
            out.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error saving data: " + e.getMessage());
        }
    }

    // Load data from files
    @SuppressWarnings("unchecked")
    void loadData() {
        try {
            ObjectInputStream in;

            File studentFile = new File("students.dat");
            if (studentFile.exists()) {
                in = new ObjectInputStream(
                    new FileInputStream(studentFile));
                students = (ArrayList<Student>) in.readObject();
                in.close();
            }

            File bookFile = new File("books.dat");
            if (bookFile.exists()) {
                in = new ObjectInputStream(
                    new FileInputStream(bookFile));
                books = (ArrayList<Book>) in.readObject();
                in.close();
            }

            File activityFile = new File("activities.dat");
            if (activityFile.exists()) {
                in = new ObjectInputStream(
                    new FileInputStream(activityFile));
                activities = (ArrayList<String[]>) in.readObject();
                in.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data: " + e.getMessage());
        }
    }

    // Button styling
    void styleButton(JButton b, Color color) {
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    JButton button(String text, Color color) {
        JButton b = new JButton(text);
        styleButton(b, color);
        return b;
    }

    JLabel heading(String text, int size) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.BOLD, size));
        l.setForeground(green);
        return l;
    }

    JTextField field() {
        JTextField f = new JTextField(20);
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return f;
    }

    JPasswordField passwordField() {
        return new JPasswordField(20);
    }

    // Welcome screen
    void welcomeScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(cream);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(
            55, 80, 45, 80));

        JLabel icon = heading("BOOKS FOR ALL", 34);
        JLabel subtitle = heading(
            "Share Knowledge • Build Futures", 19);

        JLabel description = new JLabel(
            "A little sharing can open a world of possibilities.",
            SwingConstants.CENTER);
        description.setForeground(dark);
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton student = button("STUDENT PORTAL", green);
        JButton admin = button("ADMIN PORTAL", gold);
        JButton exit = button("EXIT", dark);

        student.setAlignmentX(Component.CENTER_ALIGNMENT);
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel credentials = new JLabel(
            "Admin Login ID: admin    |    Password: admin123");
        credentials.setForeground(green);
        credentials.setFont(new Font("SansSerif", Font.BOLD, 14));
        credentials.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(icon);
        panel.add(Box.createVerticalStrut(12));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(15));
        panel.add(description);
        panel.add(Box.createVerticalStrut(40));
        panel.add(student);
        panel.add(Box.createVerticalStrut(15));
        panel.add(admin);
        panel.add(Box.createVerticalStrut(25));
        panel.add(credentials);
        panel.add(Box.createVerticalStrut(25));
        panel.add(exit);

        student.addActionListener(e -> studentLogin());
        admin.addActionListener(e -> adminLogin());
        exit.addActionListener(e -> {
            saveData();
            System.exit(0);
        });

        add(panel, BorderLayout.CENTER);
        refresh();
    }

    void refresh() {
        revalidate();
        repaint();
    }

    // Student portal
    void studentLogin() {
        String[] options = {"Login", "Create Account", "Back"};

        int choice = JOptionPane.showOptionDialog(
            this, "Welcome to the Student Portal",
            "Student Portal", JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0)
            loginForm();
        else if (choice == 1)
            createAccount();
        else
            welcomeScreen();
    }

    // Student registration
    void createAccount() {
        JTextField name = field();
        JTextField id = field();
        JPasswordField pass = passwordField();
        JTextField email = field();
        JTextField branch = field();

        JPanel p = new JPanel(new GridLayout(5, 2, 8, 8));
        p.setBackground(cream);

        p.add(new JLabel("Full Name:"));
        p.add(name);
        p.add(new JLabel("Student ID:"));
        p.add(id);
        p.add(new JLabel("Password:"));
        p.add(pass);
        p.add(new JLabel("Email:"));
        p.add(email);
        p.add(new JLabel("Branch:"));
        p.add(branch);

        int result = JOptionPane.showConfirmDialog(
            this, p, "Create Student Account",
            JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION)
            return;

        if (name.getText().trim().isEmpty()
                || id.getText().trim().isEmpty()
                || pass.getPassword().length == 0
                || email.getText().trim().isEmpty()
                || branch.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "Please fill in every field.");
            return;
        }

        for (Student s : students) {
            if (s.id.equalsIgnoreCase(id.getText().trim())) {
                JOptionPane.showMessageDialog(this,
                    "Student ID already exists.");
                return;
            }
        }

        students.add(new Student(
            name.getText().trim(),
            id.getText().trim(),
            new String(pass.getPassword()),
            email.getText().trim(),
            branch.getText().trim()));

        saveData();

        JOptionPane.showMessageDialog(this,
            "Account created successfully! Please log in.");

        studentLogin();
    }

    // Student login
    void loginForm() {
        JTextField id = field();
        JPasswordField pass = passwordField();

        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.add(new JLabel("Student ID:"));
        p.add(id);
        p.add(new JLabel("Password:"));
        p.add(pass);

        int result = JOptionPane.showConfirmDialog(
            this, p, "Student Login",
            JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) {
            studentLogin();
            return;
        }

        for (Student s : students) {
            if (s.id.equalsIgnoreCase(id.getText().trim())
                    && s.password.equals(
                        new String(pass.getPassword()))) {

                currentStudent = s;
                studentDashboard();
                return;
            }
        }

        JOptionPane.showMessageDialog(this,
            "Invalid Student ID or Password.");

        studentLogin();
    }

    // Admin login
    void adminLogin() {
        JTextField id = field();
        JPasswordField pass = passwordField();

        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.add(new JLabel("Admin ID:"));
        p.add(id);
        p.add(new JLabel("Password:"));
        p.add(pass);

        int result = JOptionPane.showConfirmDialog(
            this, p, "Admin Login",
            JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION)
            return;

        if (id.getText().equals(adminId)
                && new String(pass.getPassword()).equals(adminPassword)) {

            adminDashboard();

        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid admin credentials.");
        }
    }

    // Student dashboard
    void studentDashboard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel title = heading(
            "Welcome, " + currentStudent.name + "!", 27);
        title.setBorder(BorderFactory.createEmptyBorder(
            20, 5, 15, 5));

        add(title, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(6, 1, 10, 10));
        menu.setBackground(cream);
        menu.setBorder(BorderFactory.createEmptyBorder(
            20, 35, 25, 35));

        JButton donate = button("Donate Books", green);
        JButton receive = button("Receive Books", gold);
        JButton profile = button("My Profile", green);
        JButton activity = button("My Activity", gold);
        JButton available = button("Available Books", green);
        JButton logout = button("Logout", dark);

        menu.add(donate);
        menu.add(receive);
        menu.add(profile);
        menu.add(activity);
        menu.add(available);
        menu.add(logout);

        donate.addActionListener(e -> donateBook());
        receive.addActionListener(e -> receiveBook());
        profile.addActionListener(e -> profileScreen());
        activity.addActionListener(e ->
            showActivity(currentStudent.id));
        available.addActionListener(e -> showBooks());

        logout.addActionListener(e -> {
            currentStudent = null;
            welcomeScreen();
        });

        add(menu, BorderLayout.CENTER);
        refresh();
    }

    // Donate books
    void donateBook() {
        JTextField title = field();
        JTextField author = field();
        JTextField quantity = field();

        JPanel p = new JPanel(new GridLayout(3, 2, 8, 8));

        p.add(new JLabel("Book Title:"));
        p.add(title);
        p.add(new JLabel("Author:"));
        p.add(author);
        p.add(new JLabel("Quantity:"));
        p.add(quantity);

        int result = JOptionPane.showConfirmDialog(
            this, p, "Donate Books",
            JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION)
            return;

        try {
            String bookTitle = title.getText().trim();
            String bookAuthor = author.getText().trim();
            int qty = Integer.parseInt(quantity.getText().trim());

            if (bookTitle.isEmpty()
                    || bookAuthor.isEmpty() || qty <= 0) {

                JOptionPane.showMessageDialog(this,
                    "Enter valid book details and quantity.");
                return;
            }

            books.add(new Book(bookTitle, bookAuthor,
                currentStudent.id, qty));

            addActivity(currentStudent.id,
                "Donated " + qty + " copy/copies of " + bookTitle);

            saveData();

            JOptionPane.showMessageDialog(this,
                "Thank you for donating books!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Quantity must be a positive whole number.");
        }
    }

    // Receive books
    void receiveBook() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No books are currently available.");
            return;
        }

        String[] options = new String[books.size()];

        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            options[i] = b.title + " - " + b.author
                + " (Available: " + b.quantity + ")";
        }

        String selected = (String) JOptionPane.showInputDialog(
            this, "Select a book to request:",
            "Receive Books", JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);

        if (selected == null)
            return;

        int index = -1;

        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(selected)) {
                index = i;
                break;
            }
        }

        if (index < 0)
            return;

        Book b = books.get(index);

        if (b.quantity <= 0) {
            JOptionPane.showMessageDialog(this,
                "This book is out of stock.");
            return;
        }

        String donor = b.donor;
        b.quantity--;

        addActivity(currentStudent.id,
            "Requested 1 copy of " + b.title);

        saveData();

        JOptionPane.showMessageDialog(this,
            "Book request recorded successfully!\n"
            + "Book: " + b.title
            + "\nDonor Student ID: " + donor
            + "\nRemaining copies: " + b.quantity);
    }

    // Student profile
    void profileScreen() {
        JTextField name = new JTextField(currentStudent.name);
        JTextField email = new JTextField(currentStudent.email);
        JTextField branch = new JTextField(currentStudent.branch);

        JPanel p = new JPanel(new GridLayout(4, 2, 8, 8));

        p.add(new JLabel("Student ID:"));
        p.add(new JLabel(currentStudent.id));
        p.add(new JLabel("Full Name:"));
        p.add(name);
        p.add(new JLabel("Email:"));
        p.add(email);
        p.add(new JLabel("Branch:"));
        p.add(branch);

        int result = JOptionPane.showConfirmDialog(
            this, p, "My Profile",
            JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            if (name.getText().trim().isEmpty()
                    || email.getText().trim().isEmpty()
                    || branch.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                    "Profile fields cannot be empty.");
                return;
            }

            currentStudent.name = name.getText().trim();
            currentStudent.email = email.getText().trim();
            currentStudent.branch = branch.getText().trim();

            saveData();

            JOptionPane.showMessageDialog(this,
                "Profile updated successfully!");

            studentDashboard();
        }
    }

    // Add activity
    void addActivity(String studentId, String action) {
        activities.add(new String[]{
            studentId,
            action,
            java.time.LocalDateTime.now().toString()
        });
    }

    // Student activity history
    void showActivity(String studentId) {
        String[] columns = {
            "Student ID", "Activity", "Date and Time"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (String[] a : activities) {
            if (studentId == null || a[0].equals(studentId)) {
                model.addRow(a);
            }
        }

        showTable("Activity History", model);
    }

    // Available books
    void showBooks() {
        String[] columns = {
            "Book Title", "Author",
            "Donor ID", "Available Quantity"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (Book b : books) {
            model.addRow(new Object[]{
                b.title, b.author, b.donor, b.quantity
            });
        }

        showTable("Available Books", model);
    }

    // Display tables
    void showTable(String title, DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setBackground(cream);
        table.setForeground(dark);
        table.getTableHeader().setBackground(lightGreen);
        table.getTableHeader().setForeground(green);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(750, 350));

        JOptionPane.showMessageDialog(this, scroll,
            title, JOptionPane.PLAIN_MESSAGE);
    }

    // Admin dashboard
    void adminDashboard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel title = heading("ADMIN DASHBOARD", 28);
        title.setBorder(BorderFactory.createEmptyBorder(
            18, 5, 12, 5));

        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(cream);
        tabs.setForeground(green);

        tabs.addTab("Students", studentTable());
        tabs.addTab("Donated Books", bookTable());
        tabs.addTab("Activity History", activityTable());
        tabs.addTab("Available Books", inventoryTable());

        JButton logout = button("Logout", dark);
        logout.addActionListener(e -> welcomeScreen());

        JPanel bottom = new JPanel();
        bottom.setBackground(cream);
        bottom.add(logout);

        add(tabs, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    // Admin student table
    JScrollPane studentTable() {
        String[] columns = {
            "Student ID", "Name", "Email", "Branch"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (Student s : students) {
            model.addRow(new Object[]{
                s.id, s.name, s.email, s.branch
            });
        }

        return makeScroll(model);
    }

    // Admin donated book table
    JScrollPane bookTable() {
        String[] columns = {
            "Book Title", "Author",
            "Donor ID", "Quantity Remaining"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (Book b : books) {
            model.addRow(new Object[]{
                b.title, b.author, b.donor, b.quantity
            });
        }

        return makeScroll(model);
    }

    // Admin activity table
    JScrollPane activityTable() {
        String[] columns = {
            "Student ID", "Activity", "Date and Time"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (String[] a : activities) {
            model.addRow(a);
        }

        return makeScroll(model);
    }

    // Admin inventory table
    JScrollPane inventoryTable() {
        String[] columns = {
            "Book Title", "Author", "Available Quantity"
        };

        DefaultTableModel model =
            new DefaultTableModel(columns, 0);

        for (Book b : books) {
            model.addRow(new Object[]{
                b.title, b.author, b.quantity
            });
        }

        return makeScroll(model);
    }

    // Table formatting
    JScrollPane makeScroll(DefaultTableModel model) {
        JTable table = new JTable(model);

        table.setRowHeight(27);
        table.setBackground(cream);
        table.setForeground(dark);
        table.getTableHeader().setBackground(lightGreen);
        table.getTableHeader().setForeground(green);
        table.setAutoCreateRowSorter(true);

        return new JScrollPane(table);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new BookDonationSystem());
    }
}
