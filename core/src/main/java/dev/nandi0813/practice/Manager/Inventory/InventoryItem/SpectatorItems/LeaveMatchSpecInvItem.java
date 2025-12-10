package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems;

import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class LeaveMatchSpecInvItem extends InvItem {

    public LeaveMatchSpecInvItem() {
        super(getItemStack("SPECTATOR.MATCH.NORMAL.LEAVE.ITEM"), getInt("SPECTATOR.MATCH.NORMAL.LEAVE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Match match = MatchManager.getInstance().getLiveMatchBySpectator(player);
        if (match != null)
            match.removeSpectator(player);
    }
}
