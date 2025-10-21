public class Settings {

    private int memSeconds = 5;
    private int startLevel = 1;

    public int getMemSeconds() {
        return memSeconds;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setMemSeconds(int s) {
        memSeconds = Math.max(1, Math.min(30, s));
    }

    public void setStartLevel(int s) {
        startLevel = Math.max(1, Math.min(99, s));
    }
}
