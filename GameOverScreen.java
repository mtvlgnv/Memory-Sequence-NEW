import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends AbstractScreen {

    private final JLabel lblTitle = new JLabel("Game Over");
    private final JLabel lblStats = new JLabel("—");
    private final JButton btnAgain = new JButton("Start Again");
    private final JButton btnMenu = new JButton("Main Menu");

    public GameOverScreen(GameFrame frame) {
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 28f));
        addRow(lblTitle, 0);
        addRow(lblStats, 1);

        JPanel p = new JPanel();
        p.add(btnAgain);
        p.add(btnMenu);
        addRow(p, 2);

        btnAgain.addActionListener(e -> frame.showGame());
        btnMenu.addActionListener(e -> frame.showStart());
    }

    public void setResults(int levelsPassed, int score) {
        lblStats.setText("Levels passed: " + levelsPassed + "   |   Score: " + score);
    }
}
