package dev.nandi0813.practice.Manager.Inventory.Inventories;

import dev.nandi0813.practice.Manager.Inventory.Inventory;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.QueueItems.MatchQueueLeaveInvItem;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class MatchQueueInventory extends Inventory {

    public MatchQueueInventory() {
        super(InventoryType.MATCH_QUEUE);

        this.invItems.add(new MatchQueueLeaveInvItem());
    }

    @Override
    protected void set(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        Profile profile = ProfileManager.getInstance().getProfile(player);

        for (InvItem invItem : invItems) {
            int slot = invItem.getSlot();
            if (slot == -1)
                continue;

            if (invItem instanceof MatchQueueLeaveInvItem) {
                if (!profile.getStatus().equals(ProfileStatus.QUEUE))
                    continue;

                playerInventory.setItem(slot, invItem.getItem());
            }
        }
    }

}
