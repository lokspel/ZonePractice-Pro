package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Lobby;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.entity.Player;

public class DisableSpecMode extends InvItem {

    public DisableSpecMode() {
        super(getItemStack("SPECTATOR.LOBBY.NORMAL.DISABLE.ITEM"), getInt("SPECTATOR.LOBBY.NORMAL.DISABLE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        ProfileManager.getInstance().getProfile(player).setSpectatorMode(false);
        InventoryManager.getInstance().setLobbyInventory(player, false);
    }

}
