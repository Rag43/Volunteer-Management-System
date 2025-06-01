import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class VolunteerScreen {

    public static void volunteerScreen() {
        JFrame frame = new JFrame("Volunteers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        // Top control panel
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left-side buttons
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton homeButton = new JButton("Home");
        JButton refreshButton = new JButton("Refresh");
        homeButton.addActionListener(e -> {
            frame.dispose();
            StartScreen.startScreen();
        });
        refreshButton.addActionListener(e -> {
            frame.dispose();
            volunteerScreen();
        });
        leftButtons.add(homeButton);
        leftButtons.add(refreshButton);

        // Right-side button
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Volunteer");
        addButton.addActionListener(e -> {
            addVolunteerScreen();
        });
        rightButtons.add(addButton);

        topPanel.add(leftButtons, BorderLayout.WEST);
        topPanel.add(rightButtons, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Panel for volunteer list
        JPanel volunteerListPanel = new JPanel();
        volunteerListPanel.setLayout(new BoxLayout(volunteerListPanel, BoxLayout.Y_AXIS));
        volunteerListPanel.setBackground(Color.WHITE);

        List<Volunteer> volunteers = VolunteerFetcher.fetchVolunteers();

        for (Volunteer v : volunteers) {
            JPanel volunteerPanel = new JPanel();
            volunteerPanel.setLayout(new BorderLayout());
            volunteerPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(Color.GRAY)
            ));
            volunteerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            volunteerPanel.setBackground(new Color(250, 250, 250));

            // Info on the left
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);
            infoPanel.add(new JLabel("Name: " + v.getName()));
            infoPanel.add(new JLabel("Email: " + v.getEmail()));
            infoPanel.add(new JLabel("Phone: " + v.getPhone()));

            // Manage button on the right
            JButton manageButton = new JButton("Delete");
            manageButton.addActionListener(e -> {
                String id = v.getId();
                boolean deleted = deleteVolunteer(id);
                if (deleted) {
                    JOptionPane.showMessageDialog(null, "Volunteer Deleted Successfully. Please refresh screen.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't delete volunteer", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            });
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setOpaque(false);
            buttonPanel.add(manageButton, BorderLayout.NORTH);

            volunteerPanel.add(infoPanel, BorderLayout.WEST);
            volunteerPanel.add(buttonPanel, BorderLayout.EAST);

            // Add spacing between panels
            volunteerListPanel.add(volunteerPanel);
            volunteerListPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(volunteerListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static boolean deleteVolunteer(String id) {
        try {
            String url = "http://localhost:3000/volunteers/" + id;
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .DELETE()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addVolunteer(String name, String email, String phone) {
        try {
            Volunteer volunteer = new Volunteer(name, email, phone);
            Gson gson = new Gson();
            String json = gson.toJson(volunteer);
            HttpRequest addRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:3000/volunteers"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(addRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());

            return response.statusCode() == 200 || response.statusCode() == 201;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addVolunteerScreen() {
        JFrame addFrame = new JFrame("Add Volunteer");
        addFrame.setSize(400, 300);
        addFrame.setLayout(new BorderLayout());
        addFrame.setLocationRelativeTo(null); // Center on screen

        JLabel titleLabel = new JLabel("Enter Volunteer Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        addFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        addFrame.add(formPanel, BorderLayout.CENTER);

        // Submit button panel
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            boolean added = addVolunteer(name, email, phone);
            addFrame.dispose();
            if (added) JOptionPane.showMessageDialog(null, "Volunteer Added. Please Refresh Screen.", "Success", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null, "Couldn't Add Volunteer. Try Again Later.", "Failed", JOptionPane.ERROR_MESSAGE);

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        addFrame.add(buttonPanel, BorderLayout.SOUTH);

        addFrame.setVisible(true);
    }

}
