package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class RankedInvItem extends InvItem {

    public RankedInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.RANKED.ITEM"), getInt("LOBBY-BASIC.NORMAL.RANKED.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        player.performCommand("ranked");
    }

}
