package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.entity.Player;

public class SpectateModeInvItem extends InvItem {

    public SpectateModeInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.ENABLE-SPECTATE-MODE.ITEM"), getInt("LOBBY-BASIC.NORMAL.ENABLE-SPECTATE-MODE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        ProfileManager.getInstance().getProfile(player).setSpectatorMode(true);
        InventoryManager.getInstance().setLobbyInventory(player, false);
    }

}
