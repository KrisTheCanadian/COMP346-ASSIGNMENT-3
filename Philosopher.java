import common.BaseThread;

import java.util.Random;

/**
 * Class Philosopher.
 * Outlines main subroutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;
    public Action currentState = Action.Thinking;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done eating.
     */
    public void eat() {
        currentState = Action.Eating;
        try {
            System.out.println("Philosopher # " + this.iTID + " is now starting to eat.");
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher # " + this.iTID + " is now finished eating.");
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    public void think() {
        currentState = Action.Thinking;
        try {
            System.out.println("Philosopher # " + this.iTID + " is now starting to think.");
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher # " + this.iTID + " is now finished thinking.");
        } catch (InterruptedException e) {
            System.err.println("Philosopher.think():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk() {
        currentState = Action.Talking;
        System.out.println("Philosopher # " + this.iTID + " is now starting to to talk.");
        Thread.yield();
        saySomething();
        Thread.yield();
        System.out.println("Philosopher # " + this.iTID + " is now finished talking.");
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run() {
        for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
            Random random = new Random();
            boolean talking = random.nextBoolean();

            StartEating();

            think();

            if (talking) {
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }

            Thread.yield();
        }
    } // run()

    private void StartEating() {
        currentState = Action.Hungry;
        DiningPhilosophers.soMonitor.pickUp(getTID());
        eat();
        DiningPhilosophers.soMonitor.putDown(getTID());
    }

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething() {
        String[] astrPhrases =
                {
                        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                        "You know, true is false and false is true if you think of it",
                        "2 + 2 = 5 for extremely large values of 2...",
                        "If thee cannot speak, thee must be silent",
                        "My number is " + getTID() + ""
                };

        System.out.println
                (
                        "Philosopher " + getTID() + " says: " +
                                astrPhrases[(int) (Math.random() * astrPhrases.length)]
                );
    }
}

// EOF
