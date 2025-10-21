import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    public SettingsDialog(GameFrame owner, Settings settings) {
        super(owner, "Settings / Customization", true);
        setSize(400, 220);
        setLocationRelativeTo(owner);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        p.add(new JLabel("Memorization time (seconds):"), gbc);
        gbc.gridx = 1;
        JSpinner spTime = new JSpinner(new SpinnerNumberModel(settings.getMemSeconds(), 1, 30, 1));
        p.add(spTime, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        p.add(new JLabel("Start level:"), gbc);
        gbc.gridx = 1;
        JSpinner spStart = new JSpinner(new SpinnerNumberModel(settings.getStartLevel(), 1, 99, 1));
        p.add(spStart, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel btns = new JPanel();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        btns.add(ok); btns.add(cancel);
        p.add(btns, gbc);

        ok.addActionListener(e -> {
            settings.setMemSeconds((Integer) spTime.getValue());
            settings.setStartLevel((Integer) spStart.getValue());
            dispose();
        });
        cancel.addActionListener(e -> dispose());

        setContentPane(p);
    }
}
