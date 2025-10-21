import javax.swing.*;
import java.awt.*;

public abstract class AbstractScreen extends JPanel {

    public AbstractScreen() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
    }

    public void addRow(Component c, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.insets = new Insets(8, 8, 8, 8);
        add(c, gbc);
    }

    public void addFillRow(Component c, int gridY, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = weightY;
        add(c, gbc);
    }
}
