import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    // Screens
    private final StartScreen startScreen;
    private final GameScreen gameScreen;
    private final GameOverScreen gameOverScreen;

    // Settings/state
    final Settings settings = new Settings();

    public GameFrame() {
        super("Sequence Memory");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(720, 720);
        setLocationRelativeTo(null);

        startScreen = new StartScreen(this);
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);

        root.add(startScreen, "start");
        root.add(gameScreen, "game");
        root.add(gameOverScreen, "over");

        setContentPane(root);
        showStart();
    }

    void showStart() {
        cards.show(root, "start");
    }

    void showGame() {
        gameScreen.newGame();
        cards.show(root, "game");
        gameScreen.requestFocusInWindow();
    }

    void showGameOver(int levelsPassed, int score) {
        gameOverScreen.setResults(levelsPassed, score);
        cards.show(root, "over");
    }

    void openSettings() {
        SettingsDialog sd = new SettingsDialog(this, settings);
        sd.setVisible(true);
    }

    void openInfo() {
        InfoDialog id = new InfoDialog(this);
        id.setVisible(true);
    }
}
