package dev.nandi0813.practice.Manager.Inventory.InventoryItem.LobbyItems;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public class SettingsInvItem extends InvItem {

    public SettingsInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.SETTINGS.ITEM"), getInt("LOBBY-BASIC.NORMAL.SETTINGS.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        if (player.hasPermission("zpp.settings.open"))
            ProfileManager.getInstance().getProfile(player).getSettingsGui().open(player);
        else
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETTINGS.NO-PERMISSION"));
    }

}
