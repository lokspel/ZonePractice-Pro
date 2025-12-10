package dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.Interface;

import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventListenerInterface;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class FFAListener extends EventListenerInterface {

    @Override
    public void onPlayerQuit(Event event, PlayerQuitEvent e) {
        if (event instanceof FFAEvent ffaEvent) {
            ffaEvent.removePlayer(e.getPlayer(), true);
        }
    }

}
