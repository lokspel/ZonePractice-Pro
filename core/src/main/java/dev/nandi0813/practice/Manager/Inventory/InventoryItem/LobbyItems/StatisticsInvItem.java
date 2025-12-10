package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.GUI.GUIs.Leaderboard.LbSelectorGui;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.entity.Player;

public class StatisticsInvItem extends InvItem {

    public StatisticsInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.STATISTICS.ITEM"), getInt("LOBBY-BASIC.NORMAL.STATISTICS.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        new LbSelectorGui(player, ProfileManager.getInstance().getProfile(player))
                .open(player);
    }
}
