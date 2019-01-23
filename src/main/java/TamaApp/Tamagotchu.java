package TamaApp;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Tamagotchu implements Runnable {

    // class variables
    private static final int MAX_HEALTH = 100;
    private static final int MIN_HEALTH = 1;
    private static final int MAX_FUN = 100;
    private static final int MIN_FUN = 0;
    private static final int MAX_FULL = 100;
    private static final int MIN_FULL = 0;
    private static final int MAX_AGE = 5;
    private static final int MIN_AGE = 0;

    private static double WEIGHT_OF_FULL = 0.6;
    private static double WEIGHT_OF_FUN = 0.4;

    // instance variables
    private String name;
    private int age;
    private DoubleProperty ttlHealth = new SimpleDoubleProperty();
    private int fun;
    private int full;
    private String causeOfDeath;
    // char[][] sprite?

    Tamagotchu() {
        age = MIN_AGE;
        fun = MAX_FUN;
        full = MAX_FULL;
        ttlHealth.set(MAX_HEALTH);
        name = "Chobobo";

        // set sprite?
    }

    // runnable to decay stats
    public void run() {
        Platform.runLater(() -> this.decayStats());
    }

    void setName(String s) {
        name = s;
    }

    String getName() {
        return name;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    void increaseAge() {
        age++;
    }

    private void adjustFull(int adj) {
        full += adj;

        if(full >= MAX_FULL)
            full = MAX_FULL;

        else if(full <= MIN_FULL)
            full = MIN_FULL;

        updateTotalHealth();
    }

    private void adjustFun(int adj) {
        fun += adj;

        if(fun >= MAX_FUN)
            fun = MAX_FUN;

        else if(full <= MIN_FUN)
            fun = MIN_FUN;

        updateTotalHealth();
    }

    private void updateTotalHealth() {
        ttlHealth.set((full*WEIGHT_OF_FULL) + (fun*WEIGHT_OF_FUN));
    }

    double getTtlHealth() {
        return ttlHealth.get();
    }

    DoubleProperty ttlHealthProperty() {
        return ttlHealth;
    }

    private void decayStats() {
        full--;
        fun--;
        updateTotalHealth();
    }

    boolean isAlive() {
        if(ttlHealth.get() < MIN_HEALTH) {
            causeOfDeath = "neglect";
            return false;
        }
        else if(age > MAX_AGE) {
            causeOfDeath = "old age";
            return false;
        }
        else
            return true;
    }

    void feed() {
        // eventually parameter can be based on type of food
        adjustFull(20);
    }

    void playWith() {
        adjustFun(20);
    }

    String getStats() {
        return toString();
    }

    @Override
    public String toString() {
        return String.format("%s is %d units old. It has %d/%d fullness and %d/%d fun. Its overall health is %.0f/%d.",
                name, age, full, MAX_FULL, fun, MAX_FUN, ttlHealth.get(), MAX_HEALTH);
    }
}

