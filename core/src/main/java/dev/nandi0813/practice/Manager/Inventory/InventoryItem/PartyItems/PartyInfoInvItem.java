package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class PartyInfoInvItem extends InvItem {

    public PartyInfoInvItem() {
        super(getItemStack("PARTY.NORMAL.PARTY-INFO.ITEM"), getInt("PARTY.NORMAL.PARTY-INFO.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        player.performCommand("party info");
    }

}
