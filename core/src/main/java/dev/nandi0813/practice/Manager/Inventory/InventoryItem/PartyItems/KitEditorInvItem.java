package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class KitEditorInvItem extends InvItem {

    public KitEditorInvItem() {
        super(getItemStack("PARTY.NORMAL.PARTY-KIT-EDITOR.ITEM"), getInt("PARTY.NORMAL.PARTY-KIT-EDITOR.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        player.performCommand("editor");
    }

}
