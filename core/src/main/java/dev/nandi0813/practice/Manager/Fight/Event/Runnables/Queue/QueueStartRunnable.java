package dev.nandi0813.practice.Manager.Fight.Event.Runnables.Queue;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Interface.Runnable;
import org.bukkit.entity.Player;

public class QueueStartRunnable extends Runnable {

    private final Event event;

    public QueueStartRunnable(final QueueRunnable queueRunnable) {
        super(0, 20, false);

        this.event = queueRunnable.getEvent();
        this.seconds = this.event.getEventData().getWaitBeforeStart();
    }

    @Override
    public void run() {
        if (this.seconds == this.event.getEventData().getWaitBeforeStart() ||
                seconds == 10 ||
                seconds <= 5 &&
                        seconds != 0) {
            event.sendMessage(LanguageManager.getString("EVENT.QUEUE-START-COUNTDOWN")
                            .replaceAll("%seconds%", String.valueOf(seconds))
                            .replaceAll("%secondName%", (seconds == 1 ? LanguageManager.getString("SECOND-NAME.1SEC") : LanguageManager.getString("SECOND-NAME.1<SEC")))
                    , false);
        }

        if (seconds == 0) {
            this.cancel();
            event.start();
            event.getQueueRunnable().cancel();

            if (event.getStarter() instanceof Player starter) {
                Profile starterProfile = ProfileManager.getInstance().getProfile(starter);
                starterProfile.setEventStartLeft(starterProfile.getEventStartLeft() - 1);
            }
        }

        this.seconds--;
    }

}
