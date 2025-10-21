import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JDialog {

    public InfoDialog(GameFrame owner) {
        super(owner, "How to Play", true);
        setSize(520, 380);
        setLocationRelativeTo(owner);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setText(
            "Rules / Aims:\n\n" +
            "1) Each level shows a pattern of BLACK tiles on a grid.\n" +
            "2) You have limited seconds to memorize it (timer at the top).\n" +
            "3) After time is up, tiles turn WHITE.\n" +
            "4) Click the tiles you think were black, then press Submit.\n" +
            "5) If correct → next level; if wrong → lose a life (you have 3).\n" +
            "6) Score increases by 10 x level each time you pass a level.\n\n" +
            "Difficulty:\n" +
            "- Grid starts at 3x3.\n" +
            "- Every two levels the grid size increases by +1: 3x3, 3x3, 4x4, 4x4, ...\n" +
            "- Higher levels also show more black tiles."
        );

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());

        JPanel south = new JPanel();
        south.add(close);

        setLayout(new BorderLayout(8, 8));
        add(new JScrollPane(ta), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }
}
