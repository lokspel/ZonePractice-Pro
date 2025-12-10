package dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Match;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Inventory.Inventory;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import dev.nandi0813.practice.Util.Cooldown.PlayerCooldown;
import dev.nandi0813.practice.Util.EntityHider.PlayerHider;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ShowSpectatorsInvItem extends InvItem {

    public ShowSpectatorsInvItem() {
        super(getItemStack("SPECTATOR.MATCH.NORMAL.SHOW-SPECTATORS.ITEM"), getInt("SPECTATOR.MATCH.NORMAL.SHOW-SPECTATORS.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        if (!player.hasPermission("zpp.spectate.vanish")) {
            Common.sendMMMessage(player, LanguageManager.getString("SPECTATE.NO-PERMISSIONS"));
            return;
        }

        if (!player.hasPermission("zpp.bypass.cooldown") && PlayerCooldown.isActive(player, CooldownObject.SPECTATOR_VANISH)) {
            Common.sendMMMessage(player, StringUtil.replaceSecondString(LanguageManager.getString("SPECTATE.VANISH-COOLDOWN"), PlayerCooldown.getLeftInDouble(player, CooldownObject.SPECTATOR_VANISH)));
            return;
        } else
            PlayerCooldown.addCooldown(player, CooldownObject.SPECTATOR_VANISH, ConfigManager.getInt("SPECTATOR-SETTINGS.VANISH-COOLDOWN"));

        Profile profile = ProfileManager.getInstance().getProfile(player);

        profile.setHideSpectators(!profile.isHideSpectators());
        InventoryManager.getInstance().setInventory(player, Inventory.InventoryType.SPECTATE_MATCH);
        PlayerHider.getInstance().toggleSpectatorVisibility(player);
    }
}
