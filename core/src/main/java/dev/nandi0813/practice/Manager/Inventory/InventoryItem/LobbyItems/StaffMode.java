package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.entity.Player;

public class StaffMode extends InvItem {

    public StaffMode() {
        super(getItemStack("LOBBY-BASIC.NORMAL.STAFF-MODE.ITEM"), getInt("LOBBY-BASIC.NORMAL.STAFF-MODE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        ProfileManager.getInstance().getProfile(player).setStaffMode(true);
        InventoryManager.getInstance().setLobbyInventory(player, false);
    }

}
