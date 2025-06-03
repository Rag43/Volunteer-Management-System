import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EntryScreen {

    public static void entryScreen() {
        // Create frame
        JFrame frame = new JFrame("Entry Screen");
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(new BorderLayout());

        // Top panel with Home button and title
        JPanel topPanel = new JPanel(new BorderLayout());

        // Home button (top-left)
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> {
            frame.dispose();
            StartScreen.startScreen();
        });
        topPanel.add(homeButton, BorderLayout.WEST);

        // Title label (center)
        JLabel titleLabel = new JLabel("Entry Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // Panel with 3 vertically aligned buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Padding

        JButton approvedButton = new JButton("Approved Entries");
        approvedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        approvedButton.addActionListener(e -> {
            // TODO: Handle Approved Entries
            frame.dispose();
            approvedEntries();
        });

        JButton deniedButton = new JButton("Denied Entries");
        deniedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deniedButton.addActionListener(e -> {
            // TODO: Handle Denied Entries
            frame.dispose();
            deniedEntries();
        });

        JButton pendingButton = new JButton("Pending Entries");
        pendingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pendingButton.addActionListener(e -> {
            // TODO: Handle Pending Entries
            frame.dispose();
            pendingEntries();
        });

        // Add buttons with spacing
        buttonPanel.add(approvedButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deniedButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(pendingButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void approvedEntries() {
        JFrame frame = new JFrame("Approved Entries");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel (Home, Refresh)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh");

        backButton.addActionListener(e -> {
            frame.dispose();
            entryScreen();
        });
        refreshButton.addActionListener(e -> {
            frame.dispose();
            approvedEntries();
        });

        topPanel.add(backButton);
        topPanel.add(refreshButton);

        // Title
        JLabel titleLabel = new JLabel("Approved Entries");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(topPanel, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH);

        // Scrollable Entries Panel
        JPanel entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));

        List<Entry> approvedEntries = EntryManager.fetchApprovedEntries();
        for (Entry entry : approvedEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new BorderLayout());
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            entryPanel.setPreferredSize(new Dimension(750, 80));

            JTextArea infoArea = new JTextArea();
            infoArea.setEditable(false);
            infoArea.setText(
                    "Volunteer: " + entry.getVolunteer().getName() + "\n" +
                            "Title: " + entry.getTitle() + "\n" +
                            "Description: " + entry.getDescription() + "\n" +
                            "Hours: " + entry.getHours() + "\n" +
                            "Approved: " + entry.getApproved()
            );

            JButton denyButton = new JButton("Deny Entry");
            denyButton.addActionListener(e -> {
                boolean denied = EntryManager.denyEntry(entry);
                if (denied) {
                    JOptionPane.showMessageDialog(null, "Entry Denied.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh screen automatically
                    frame.dispose();
                    approvedEntries();
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't Deny Entry. Please Try Again Later.", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            entryPanel.add(infoArea, BorderLayout.CENTER);
            entryPanel.add(denyButton, BorderLayout.EAST);

            entriesPanel.add(entryPanel);
            entriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void deniedEntries() {
        JFrame frame = new JFrame("Denied Entries");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel (Home, Refresh)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh");

        backButton.addActionListener(e -> {
            frame.dispose();
            entryScreen();
        });
        refreshButton.addActionListener(e -> {
            frame.dispose();
            deniedEntries();
        });

        topPanel.add(backButton);
        topPanel.add(refreshButton);

        // Title
        JLabel titleLabel = new JLabel("Denied Entries");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(topPanel, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH);

        // Scrollable Entries Panel
        JPanel entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));

        List<Entry> deniedEntries = EntryManager.fetchDeniedEntries();
        for (Entry entry : deniedEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new BorderLayout());
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            entryPanel.setPreferredSize(new Dimension(750, 80));

            JTextArea infoArea = new JTextArea();
            infoArea.setEditable(false);
            infoArea.setText(
                    "Volunteer: " + entry.getVolunteer().getName() + "\n" +
                            "Title: " + entry.getTitle() + "\n" +
                            "Description: " + entry.getDescription() + "\n" +
                            "Hours: " + entry.getHours() + "\n" +
                            "Approved: " + entry.getApproved()
            );

            JButton denyButton = new JButton("Approve Entry");
            denyButton.addActionListener(e -> {
                boolean denied = EntryManager.approveEntry(entry); // Call approveEntry here and update message
                if (denied) {
                    JOptionPane.showMessageDialog(null, "Entry Approved.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh screen automatically
                    frame.dispose();
                    deniedEntries();
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't Approve Entry. Please Try Again Later.", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            entryPanel.add(infoArea, BorderLayout.CENTER);
            entryPanel.add(denyButton, BorderLayout.EAST);

            entriesPanel.add(entryPanel);
            entriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void pendingEntries() {
        JFrame frame = new JFrame("Pending Entries");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel (Back, Refresh)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh");

        backButton.addActionListener(e -> {
            frame.dispose();
            entryScreen();
        });
        refreshButton.addActionListener(e -> {
            frame.dispose();
            pendingEntries();
        });

        topPanel.add(backButton);
        topPanel.add(refreshButton);

        // Title
        JLabel titleLabel = new JLabel("Pending Entries");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(topPanel, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH);

        // Scrollable Entries Panel
        JPanel entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));

        List<Entry> pendingEntries = EntryManager.fetchPendingEntries();
        for (Entry entry : pendingEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new BorderLayout());
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            entryPanel.setPreferredSize(new Dimension(750, 100));

            JTextArea infoArea = new JTextArea();
            infoArea.setEditable(false);
            infoArea.setText(
                    "Volunteer: " + entry.getVolunteer().getName() + "\n" +
                            "Title: " + entry.getTitle() + "\n" +
                            "Description: " + entry.getDescription() + "\n" +
                            "Hours: " + entry.getHours() + "\n" +
                            "Approved: " + entry.getApproved()
            );

            // Button panel (stacked vertically)
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            JButton approveButton = new JButton("Approve Entry");
            approveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            approveButton.addActionListener(e -> {
                boolean success = EntryManager.approveEntry(entry);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Entry Approved.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    pendingEntries();
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't Approve Entry. Please Try Again Later.", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton denyButton = new JButton("Deny Entry");
            denyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            denyButton.addActionListener(e -> {
                boolean success = EntryManager.denyEntry(entry);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Entry Denied.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    pendingEntries();
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't Deny Entry. Please Try Again Later.", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            buttonPanel.add(approveButton);
            buttonPanel.add(Box.createVerticalStrut(5));
            buttonPanel.add(denyButton);

            entryPanel.add(infoArea, BorderLayout.CENTER);
            entryPanel.add(buttonPanel, BorderLayout.EAST);

            entriesPanel.add(entryPanel);
            entriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }


}
