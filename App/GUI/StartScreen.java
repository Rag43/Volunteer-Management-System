import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {

    public static void startScreen() {
        // Create the main frame
        JFrame frame = new JFrame("Volunteer Management Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);  // Slightly reduced height
        frame.setLocationRelativeTo(null);  // Center the window

        // Create a panel with vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding

        // Create and add the title label
        JLabel titleLabel = new JLabel("Volunteer Management Hub", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Create buttons
        JButton volunteersButton = new JButton("Volunteers");
        JButton sessionsButton = new JButton("Session Entries");

        // Center-align buttons
        volunteersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sessionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners
        volunteersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                VolunteerScreen.volunteerScreen();
            }
        });

        sessionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                EntryScreen.entryScreen();
            }
        });

        // Add buttons to the panel with spacing
        panel.add(volunteersButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(sessionsButton);

        // Add panel to frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        startScreen();
    }
}
