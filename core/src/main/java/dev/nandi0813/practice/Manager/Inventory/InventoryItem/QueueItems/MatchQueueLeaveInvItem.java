package dev.nandi0813.practice.Manager.Inventory.InventoryItem.QueueItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Queue.Queue;
import dev.nandi0813.practice.Manager.Queue.QueueManager;
import org.bukkit.entity.Player;

public class MatchQueueLeaveInvItem extends InvItem {

    public MatchQueueLeaveInvItem() {
        super(getItemStack("QUEUE.MATCH.NORMAL.LEAVE-MATCH-QUEUE.ITEM"), getInt("QUEUE.MATCH.NORMAL.LEAVE-MATCH-QUEUE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Queue queue = QueueManager.getInstance().getQueue(player);
        if (queue == null)
            return;

        queue.endQueue(false, null);
    }

}
