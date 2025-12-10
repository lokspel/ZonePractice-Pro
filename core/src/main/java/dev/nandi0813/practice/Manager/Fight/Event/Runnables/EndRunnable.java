package dev.nandi0813.practice.Manager.Fight.Event.Runnables;

import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Util.Interface.Runnable;

public class EndRunnable extends Runnable {

    private final Event event;

    public EndRunnable(final Event event) {
        super(0, 20, false);

        this.event = event;
        this.seconds = 5;
    }

    @Override
    public void run() {
        if (this.seconds == 0) {
            this.end();
        } else {
            this.seconds--;
        }
    }

    public void end() {
        this.cancel();

        this.event.removeAll();
        this.event.getFightChange().rollback(100, 50);

        EventManager.getInstance().getEvents().remove(event);
    }

}
