package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Lobby;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class SpecMenuInvItem extends InvItem {

    public SpecMenuInvItem() {
        super(getItemStack("SPECTATOR.LOBBY.NORMAL.MENU.ITEM"), getInt("SPECTATOR.LOBBY.NORMAL.MENU.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        SpectatorManager.getInstance().spectateMenuUse(player);
    }

}
