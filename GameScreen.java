import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.HashSet;

public class GameScreen extends AbstractScreen implements RoundLifecycle {

    private final GameFrame frame;

    // HUD
    private final JLabel lblTimer = new JLabel("Time: 0");
    private final JLabel lblLives = new JLabel("Lives: 3");
    private final JLabel lblLevel = new JLabel("Level: 1");
    private final JLabel lblScore = new JLabel("Score: 0");
    private final JLabel lblPhase = new JLabel("Phase: —");

    // Controls
    private final JButton btnSubmit = new JButton("Submit");
    private final JButton btnNext = new JButton("Next");

    // Grid
    private JPanel gridPanel;
    private TileButton[] tiles;
    private int gridSizeCurrent = 3;

    // State
    private int lives;
    private int level;
    private int score;
    private int memSecondsLeft;
    private boolean inMemorize;
    private boolean inSelect;
    private javax.swing.Timer secondTimer;

    private final Random rng = new Random();

    public GameScreen(GameFrame frame) {
        this.frame = frame;

        // HUD row
        JPanel hud = new JPanel(new GridLayout(1, 5, 8, 8));
        hud.add(lblTimer);
        hud.add(lblLives);
        hud.add(lblLevel);
        hud.add(lblScore);
        hud.add(lblPhase);
        addRow(hud, 0);

        // Grid holder
        gridPanel = new JPanel();
        gridPanel.setPreferredSize(new Dimension(420, 420));
        addFillRow(gridPanel, 1, 1.0);

        // Buttons row
        JPanel controls = new JPanel();
        controls.add(btnSubmit);
        controls.add(btnNext);
        addRow(controls, 2);

        btnSubmit.addActionListener(e -> onSubmit());
        btnNext.addActionListener(e -> onNext());

        btnSubmit.setEnabled(false);
        btnNext.setEnabled(false);
    }

    void newGame() {
        lives = 3;
        level = frame.settings.getStartLevel();
        score = 0;

        ensureGridForLevel(level);
        updateHud();
        startRound();
    }

    // Grid sizes change every 2 levels: 3x3, 3x3, 4x4, 4x4, 5x5, 5x5, etc. Max at 9
    private int gridSizeForLevel(int lvl) {
        int inc = (Math.max(1, lvl) - 1) / 2;
        return Math.min(9, 3 + inc);
    }

    // Increasing the number of black tiles. Max at half the board
    private int blackCountFor(int lvl, int totalTiles) {
        return Math.min(2 + lvl, Math.max(1, totalTiles / 2));
    }

    private void ensureGridForLevel(int lvl) {
        int want = gridSizeForLevel(lvl);
        if (want == gridSizeCurrent && tiles != null) {
            return;
        }

        gridSizeCurrent = want;
        remove(gridPanel);

        gridPanel = new JPanel(new GridLayout(gridSizeCurrent, gridSizeCurrent, 4, 4));
        gridPanel.setPreferredSize(new Dimension(420, 420));
        tiles = new TileButton[gridSizeCurrent * gridSizeCurrent];

        for (int i = 0; i < tiles.length; i++) {
            TileButton t = new TileButton(i);
            t.addActionListener(e -> {
                if (inSelect) {
                    t.setSelectedByPlayer(!t.isSelectedByPlayer());
                }
            });
            tiles[i] = t;
            gridPanel.add(t);
        }

        addFillRow(gridPanel, 1, 1.0);
        revalidate();
        repaint();
    }

    @Override
    public void startRound() {
        inMemorize = true;
        inSelect = false;
        btnSubmit.setEnabled(false);
        btnNext.setEnabled(false);

        // If level crossed a size boundary, rebuild to larger grid
        ensureGridForLevel(level);

        // Reset tiles
        for (TileButton t : tiles) {
            t.setTarget(false);
            t.setSelectedByPlayer(false);
            t.showWhite();
        }

        int totalTiles = gridSizeCurrent * gridSizeCurrent;
        int k = blackCountFor(level, totalTiles);

        HashSet<Integer> chosen = new HashSet<>();

        while (chosen.size() < k) {
            int row = rng.nextInt(gridSizeCurrent);
            int col = rng.nextInt(gridSizeCurrent);
            int index = row * gridSizeCurrent + col;
            chosen.add(index);
        }

        for (int idx : chosen) {
            tiles[idx].setTarget(true);
            tiles[idx].showBlack();
        }

        lblPhase.setText("Phase: Memorize");
        memSecondsLeft = Math.max(1, frame.settings.getMemSeconds());
        lblTimer.setText("Time: " + memSecondsLeft);

        if (secondTimer != null && secondTimer.isRunning()) {
            secondTimer.stop();
        }

        secondTimer = new javax.swing.Timer(1000, e -> {
            memSecondsLeft--;
            lblTimer.setText("Time: " + memSecondsLeft);
            if (memSecondsLeft <= 0) {
                secondTimer.stop();
                enterSelectPhase();
            }
        });

        secondTimer.start();
    }

    @Override
    public void enterSelectPhase() {
        inMemorize = false;
        inSelect = true;
        lblPhase.setText("Phase: Select");

        for (TileButton t : tiles) {
            t.showWhite();
        }

        btnSubmit.setEnabled(true);
        btnNext.setEnabled(false);
    }

    private void onSubmit() {
        if (!inSelect) {
            return;
        }

        boolean correct = true;
        for (TileButton t : tiles) {
            if (t.isTarget() != t.isSelectedByPlayer()) {
                correct = false;
                break;
            }
        }

        for (TileButton t : tiles) {
            t.revealResult();
        }

        if (correct) {
            score += level * 10;
            lblPhase.setText("Correct! (+" + (level * 10) + ")");
            level++;
            inSelect = false;
            btnSubmit.setEnabled(false);
            btnNext.setEnabled(true);
            updateHud();
        } else {
            lives--;
            lblPhase.setText("Wrong! (-1 life)");
            inSelect = false;
            btnSubmit.setEnabled(false);
            btnNext.setEnabled(true);
            updateHud();

            if (lives <= 0) {
                Timer delay = new Timer(800, e -> {
                    int levelsPassed = Math.max(0, level - frame.settings.getStartLevel());
                    frame.showGameOver(levelsPassed, score);
                });
                delay.setRepeats(false);
                delay.start();
            }
        }
    }

    private void onNext() {
        if (lives <= 0) {
            return;
        }
        startRound();
    }

    private void updateHud() {
        lblLives.setText("Lives: " + lives);
        lblLevel.setText("Level: " + level + "  (" + gridSizeCurrent + "x" + gridSizeCurrent + ")");
        lblScore.setText("Score: " + score);
    }
}
