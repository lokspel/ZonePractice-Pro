package dev.nandi0813.practice.Manager.Inventory.InventoryItem.PartyItems;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public class PartySettingsInvItem extends InvItem {

    public PartySettingsInvItem() {
        super(getItemStack("PARTY.NORMAL.PARTY-SETTINGS.ITEM"), getInt("PARTY.NORMAL.PARTY-SETTINGS.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) return;

        if (!party.getLeader().equals(player)) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NOT-LEADER"));
            return;
        }

        party.getPartySettingsGui().open(player);
    }
}
