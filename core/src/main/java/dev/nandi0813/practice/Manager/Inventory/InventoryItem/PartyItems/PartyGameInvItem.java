package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public class PartyGameInvItem extends InvItem {

    public PartyGameInvItem() {
        super(getItemStack("PARTY.NORMAL.HOST-PARTY-GAME.ITEM"), getInt("PARTY.NORMAL.HOST-PARTY-GAME.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) return;

        if (!party.getLeader().equals(player)) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NOT-LEADER"));
            return;
        }

        if (party.getMembers().size() < 2) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NOT-ENOUGH-PLAYER-FOR-GAME"));
            return;
        }

        GUIManager.getInstance().searchGUI(GUIType.Party_Events).open(player);
    }

}
