package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Fight.Match.Util.RematchRequest;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class RematchInvItem extends InvItem {

    public RematchInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.REMATCH.ITEM"), getInt("LOBBY-BASIC.NORMAL.REMATCH.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        RematchRequest rematchRequest = MatchManager.getInstance().getRematchRequest(player);

        if (rematchRequest != null)
            rematchRequest.sendRematchRequest(player);
    }

}
