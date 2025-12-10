package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems;

import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Fight.FFA.FFAManager;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import org.bukkit.entity.Player;

public class LeaveFfaSpecInvItem extends InvItem {

    public LeaveFfaSpecInvItem() {
        super(getItemStack("SPECTATOR.FFA.NORMAL.LEAVE.ITEM"), getInt("SPECTATOR.FFA.NORMAL.LEAVE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        FFA ffa = FFAManager.getInstance().getFFABySpectator(player);
        if (ffa != null)
            ffa.removeSpectator(player);
    }

}
