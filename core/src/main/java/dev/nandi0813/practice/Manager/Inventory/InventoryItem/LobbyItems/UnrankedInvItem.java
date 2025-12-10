package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class UnrankedInvItem extends InvItem {

    public UnrankedInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.UNRANKED.ITEM"), getInt("LOBBY-BASIC.NORMAL.UNRANKED.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        player.performCommand("unranked");
    }

}
