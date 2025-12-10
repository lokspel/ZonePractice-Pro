package dev.nandi0813.practice.Manager.Inventory.InventoryItem.StaffItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Manager.Spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class ModeOffInvItem extends InvItem {

    public ModeOffInvItem() {
        super(getItemStack("STAFF-MODE.NORMAL.TURN-OFF.ITEM"), getInt("STAFF-MODE.NORMAL.TURN-OFF.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        ProfileManager.getInstance().getProfile(player).setStaffMode(false);

        if (SpectatorManager.getInstance().getSpectators().containsKey(player)) {
            SpectatorManager.getInstance().getSpectators().get(player).removeSpectator(player);
        } else {
            InventoryManager.getInstance().setLobbyInventory(player, false);
        }
    }
}
