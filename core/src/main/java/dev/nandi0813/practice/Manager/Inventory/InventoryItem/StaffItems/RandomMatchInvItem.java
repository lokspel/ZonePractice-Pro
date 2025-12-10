package dev.nandi0813.practice.Manager.Inventory.InventoryItem.StaffItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class RandomMatchInvItem extends InvItem {

    public RandomMatchInvItem() {
        super(getItemStack("STAFF-MODE.NORMAL.RANDOM-GAME-SPECTATE.ITEM"), getInt("STAFF-MODE.NORMAL.RANDOM-GAME-SPECTATE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        SpectatorManager.spectateRandomMatch(player);
    }

}
