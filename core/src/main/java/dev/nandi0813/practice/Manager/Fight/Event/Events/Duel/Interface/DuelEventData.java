package dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Interface;

import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventData;
import org.bukkit.Location;

public abstract class DuelEventData extends EventData {

    public DuelEventData(EventType type) {
        super(type);
    }

    public Location getLocation1() {
        return spawns.get(0);
    }

    public Location getLocation2() {
        return spawns.get(1);
    }

}
