package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public class OtherPartiesInvItem extends InvItem {

    public OtherPartiesInvItem() {
        super(getItemStack("PARTY.NORMAL.OTHER-PARTIES.ITEM"), getInt("PARTY.NORMAL.OTHER-PARTIES.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) return;

        if (!party.getLeader().equals(player)) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NOT-LEADER"));
            return;
        }

        GUIManager.getInstance().searchGUI(GUIType.Party_OtherParties).open(player);
    }

}
