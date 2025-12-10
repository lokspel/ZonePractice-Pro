package dev.nandi0813.practice.Manager.Inventory.InventoryItem.StaffItems;

import dev.nandi0813.practice.Manager.GUI.GUIs.PlayerInvGui;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class CheckInventoryInvItem extends InvItem {

    public CheckInventoryInvItem() {
        super(getItemStack("STAFF-MODE.NORMAL.PLAYER-INVENTORY.ITEM"), getInt("STAFF-MODE.NORMAL.PLAYER-INVENTORY.SLOT"));
    }

    public void handleClickEvent(Player player, Player target) {
        if (target != null)
            new PlayerInvGui(target).open(player);
    }

    @Override
    public void handleClickEvent(Player player) {
    }

}
