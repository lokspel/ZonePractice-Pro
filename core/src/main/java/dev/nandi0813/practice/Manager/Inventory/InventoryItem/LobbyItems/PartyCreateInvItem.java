package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import org.bukkit.entity.Player;

public class PartyCreateInvItem extends InvItem {

    public PartyCreateInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.PARTY-CREATE.ITEM"), getInt("LOBBY-BASIC.NORMAL.PARTY-CREATE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        PartyManager.getInstance().createParty(player);
    }

}
