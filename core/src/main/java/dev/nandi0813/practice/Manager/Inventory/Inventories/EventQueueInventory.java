package dev.nandi0813.practice.Manager.Inventory.Inventories;

import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Inventory.Inventory;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.QueueItems.EventQueueLeaveInvItem;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class EventQueueInventory extends Inventory {

    public EventQueueInventory() {
        super(InventoryType.EVENT_QUEUE);

        this.invItems.add(new EventQueueLeaveInvItem());
    }

    @Override
    protected void set(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        for (InvItem invItem : invItems) {
            int slot = invItem.getSlot();
            if (slot == -1)
                continue;

            if (invItem instanceof EventQueueLeaveInvItem) {
                Event event = EventManager.getInstance().getEventByPlayer(player);
                if (event == null)
                    continue;

                ItemStack item = invItem.getItem().clone();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(StringUtil.CC(itemMeta.getDisplayName().replaceAll("%event%", event.getType().getName())));
                item.setItemMeta(itemMeta);

                playerInventory.setItem(slot, item);
            }
        }
    }

}
