import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardGUI extends JFrame {

    private JTextField inputField;
    private DefaultTableModel tableModel;

    public LeaderboardGUI() {

        setTitle("Pyramid Leaderboard Generator");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------- TOP PANEL ----------------
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel label = new JLabel("Total Players:");
        inputField = new JTextField(10);
        JButton generateBtn = new JButton("Generate");

        topPanel.add(label);
        topPanel.add(inputField);
        topPanel.add(generateBtn);

        add(topPanel, BorderLayout.NORTH);

        // ---------------- TABLE ----------------
        tableModel = new DefaultTableModel(
                new Object[]{"Tier", "Name", "Rank Range", "Players"}, 0
        );

        JTable table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ---------------- BUTTON ACTION ----------------
        generateBtn.addActionListener(e -> generateLeaderboard());

        // ---------------- SIMPLE STYLING ----------------
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void generateLeaderboard() {

        tableModel.setRowCount(0);

        int totalPlayers;

        try {
            totalPlayers = Integer.parseInt(inputField.getText());

            if (totalPlayers < 1) {
                JOptionPane.showMessageDialog(this,
                        "Enter a number greater than 0.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid integer.");
            return;
        }

        // ---------------- CALL SHARED LOGIC ----------------
        List<DistributionCalculator.TierResult> results =
                DistributionCalculator.generate(totalPlayers);

        // ---------------- POPULATE TABLE ----------------
        for (DistributionCalculator.TierResult t : results) {

            String range = (t.startRank == t.endRank)
                    ? "Rank " + t.startRank
                    : "Ranks " + t.startRank + " - " + t.endRank;

            tableModel.addRow(new Object[]{
                    t.code,
                    t.name,
                    range,
                    t.players
            });
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LeaderboardGUI gui = new LeaderboardGUI();
            gui.setVisible(true);
        });
    }
}