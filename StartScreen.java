import javax.swing.*;
import java.awt.*;

public class StartScreen extends AbstractScreen {
    public StartScreen(GameFrame frame) {
        JLabel title = new JLabel("Sequence Memory");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        addRow(title, 0);

        JButton start = new JButton("Start");
        start.addActionListener(e -> frame.showGame());
        addRow(start, 1);

        JButton settings = new JButton("Settings / Customization");
        settings.addActionListener(e -> frame.openSettings());
        addRow(settings, 2);

        JButton info = new JButton("Info");
        info.addActionListener(e -> frame.openInfo());
        addRow(info, 3);
    }
}
