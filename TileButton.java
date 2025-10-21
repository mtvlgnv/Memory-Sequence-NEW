import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {

    private final int index;
    private boolean isTarget;
    private boolean isSelected;

    public TileButton(int index) {
        this.index = index;
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        setPreferredSize(new Dimension(48, 48));
        setMinimumSize(new Dimension(36, 36));
    }

    public int getIndex() {
        return index;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean v) {
        isTarget = v;
    }

    public boolean isSelectedByPlayer() {
        return isSelected;
    }

    public void setSelectedByPlayer(boolean v) {
        isSelected = v;
        setBackground(isSelected ? new Color(170, 220, 230) : Color.WHITE);
    }

    public void showBlack() {
        setBackground(Color.BLACK);
    }

    public void showWhite() {
        setBackground(Color.WHITE);
    }

    public void revealResult() {
        // green == correct pick; red == false positive; blue == missed target
        if (isTarget && isSelected) {
            setBackground(new Color(120, 200, 120));
        } else if (!isTarget && isSelected) {
            setBackground(new Color(220, 120, 120));
        } else if (isTarget && !isSelected) {
            setBackground(new Color(120, 120, 220));
        } else {
            setBackground(Color.WHITE);
        }
    }
}
