package dev.nandi0813.practice.Manager.Inventory.InventoryItem.StaffItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.EntityHider.PlayerHider;
import org.bukkit.entity.Player;

public class HideOnInvItem extends InvItem {

    public HideOnInvItem() {
        super(getItemStack("STAFF-MODE.NORMAL.HIDE-FROM-PLAYERS-ON.ITEM"), getInt("STAFF-MODE.NORMAL.HIDE-FROM-PLAYERS-ON.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Profile profile = ProfileManager.getInstance().getProfile(player);
        profile.setHideFromPlayers(!profile.isHideFromPlayers());

        PlayerHider.getInstance().toggleStaffVisibility(player);
        InventoryManager.getInstance().setStaffModeInventory(player);
    }

}
