package dev.nandi0813.practice.Manager.Inventory.InventoryItem.QueueItems;

import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class EventQueueLeaveInvItem extends InvItem {

    public EventQueueLeaveInvItem() {
        super(getItemStack("QUEUE.EVENT.NORMAL.LEAVE-EVENT-QUEUE.ITEM"), getInt("QUEUE.EVENT.NORMAL.LEAVE-EVENT-QUEUE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Event event = EventManager.getInstance().getEventByPlayer(player);
        if (event != null)
            event.removePlayer(player, true);
    }
}
