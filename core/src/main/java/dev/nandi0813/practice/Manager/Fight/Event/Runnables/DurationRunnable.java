package dev.nandi0813.practice.Manager.Fight.Event.Runnables;

import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Util.Interface.Runnable;

public class DurationRunnable extends Runnable {

    private final Event event;

    public DurationRunnable(final Event event) {
        super(0, 20, false);
        this.event = event;
        this.seconds = event.getEventData().getDuration();
    }

    @Override
    public void run() {
        event.handleDurationRunnable(this);
    }

    public void decreaseTime() {
        this.seconds--;
    }

}
