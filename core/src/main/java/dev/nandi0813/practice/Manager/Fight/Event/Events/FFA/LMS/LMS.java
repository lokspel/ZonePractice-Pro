package dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.LMS;

import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.Interface.FFAEvent;
import dev.nandi0813.practice.Module.Interfaces.KitData;
import org.bukkit.entity.Player;

public class LMS extends FFAEvent {

    public LMS(Object starter, LMSData eventData) {
        super(starter, eventData, "COMMAND.EVENT.ARGUMENTS.LMS");
    }

    @Override
    public LMSData getEventData() {
        return (LMSData) eventData;
    }

    @Override
    protected void customCustomStart() {
    }

    @Override
    protected void loadInventory(Player player) {
        KitData kitData = this.getEventData().getKitData();
        kitData.loadKitData(player, true);
    }

}
