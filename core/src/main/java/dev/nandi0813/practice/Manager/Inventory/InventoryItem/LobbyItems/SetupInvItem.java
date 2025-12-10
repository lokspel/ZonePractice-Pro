package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class SetupInvItem extends InvItem {

    public SetupInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.SETUP.ITEM"), getInt("LOBBY-BASIC.NORMAL.SETUP.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        player.performCommand("setup");
    }

}
