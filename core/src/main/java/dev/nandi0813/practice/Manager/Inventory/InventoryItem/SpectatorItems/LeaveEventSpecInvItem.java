package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems;

import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class LeaveEventSpecInvItem extends InvItem {

    public LeaveEventSpecInvItem() {
        super(getItemStack("SPECTATOR.EVENT.NORMAL.LEAVE.ITEM"), getInt("SPECTATOR.EVENT.NORMAL.LEAVE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Event event = EventManager.getInstance().getEventBySpectator(player);
        if (event != null)
            event.removeSpectator(player);
    }

}
