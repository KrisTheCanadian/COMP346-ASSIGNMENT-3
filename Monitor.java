/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    Chopstick[] chopsticks;
    boolean talking = false;

    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        chopsticks = new Chopstick[piNumberOfPhilosophers];
        for (int i = 0; i < chopsticks.length; ++i) {
            chopsticks[i] = new Chopstick(i);
        }
    }

    /*
     * -------------------------------
     * User-defined monitor procedures
     * -------------------------------
     */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */

    public synchronized void pickUp(final int piTID) {
        int chopstick1Id = piTID % chopsticks.length;
        int chopstick2Id = (piTID - 1) % chopsticks.length;

        //Philosopher 1

        while(chopsticks[Math.min(chopstick1Id, chopstick2Id)].usedBy() != -1 || chopsticks[Math.max(chopstick1Id, chopstick2Id)].usedBy() != -1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        chopsticks[chopstick2Id].usedBy(piTID);
        chopsticks[chopstick1Id].usedBy(piTID);

    }

    /**
     * When a given philosopher's done eating, they put the chopsticks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID) {
        int chopstick1Id = piTID % chopsticks.length;
        int chopstick2Id = (piTID - 1) % chopsticks.length;
        chopsticks[chopstick2Id].usedBy(-1);
        chopsticks[chopstick1Id].usedBy(-1);
        notifyAll();
    }

    /**
     * Only one philosopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk() {
        if(talking){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk() {
        talking = false;
        notifyAll();
    }
}

// EOF
