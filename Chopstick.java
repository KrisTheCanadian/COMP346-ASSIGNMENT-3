public class Chopstick {

    private final int position;
    private int Used_by_piTID = -1;

    Chopstick(int position) { this.position = position;
    }

    void usedBy(int Used_by_piTID) {
        this.Used_by_piTID = Used_by_piTID;
    }

    int usedBy() {
        return Used_by_piTID;
    }
}
