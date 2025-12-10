package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import org.bukkit.entity.Player;

public class LeavePartyInvItem extends InvItem {

    public LeavePartyInvItem() {
        super(getItemStack("PARTY.NORMAL.LEAVE-PARTY.ITEM"), getInt("PARTY.NORMAL.LEAVE-PARTY.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) return;

        party.removeMember(player, false);
    }

}
