package dev.nandi0813.practice.Manager.Inventory.InventoryItem.StaffItems;

import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Spectator.SpectatorManager;
import dev.nandi0813.practice.Util.Interface.Spectatable;
import org.bukkit.entity.Player;

public class LeaveSpecInvItem extends InvItem {

    public LeaveSpecInvItem() {
        super(getItemStack("STAFF-MODE.NORMAL.LEAVE-SPECTATE.ITEM"), getInt("STAFF-MODE.NORMAL.LEAVE-SPECTATE.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        Spectatable spectatable = SpectatorManager.getInstance().getSpectators().get(player);
        if (spectatable != null)
            spectatable.removeSpectator(player);
    }
}
