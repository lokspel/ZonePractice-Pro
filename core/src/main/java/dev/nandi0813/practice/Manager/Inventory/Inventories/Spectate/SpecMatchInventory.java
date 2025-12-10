package dev.nandi0813.practice.Manager.Inventory.Inventories.Spectate;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Inventory.Inventory;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.InvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.LeaveMatchSpecInvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Match.HideSpectatorsInvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Match.RandomMatchInvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Match.ShowSpectatorsInvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryItem.SpectatorItems.SpectatorModeItems.Match.SpecMenuInvItem;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.PlayerUtil.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class SpecMatchInventory extends Inventory {

    private static final double SPECTATOR_SPEED = ConfigManager.getDouble("SPECTATOR-SETTINGS.SPECTATOR-SPEED");

    public SpecMatchInventory() {
        super(InventoryType.SPECTATE_MATCH);

        this.invItems.add(new HideSpectatorsInvItem());
        this.invItems.add(new RandomMatchInvItem());
        this.invItems.add(new ShowSpectatorsInvItem());
        this.invItems.add(new SpecMenuInvItem());
        this.invItems.add(new LeaveMatchSpecInvItem());
    }

    @Override
    protected void set(Player player) {
        PlayerUtil.clearPlayer(player, true, true, false);
        player.setFlySpeed((float) SPECTATOR_SPEED / 10);

        Profile profile = ProfileManager.getInstance().getProfile(player);
        profile.setStatus(ProfileStatus.SPECTATE);

        PlayerInventory playerInventory = player.getInventory();

        for (InvItem invItem : this.invItems) {
            int slot = invItem.getSlot();
            if (slot == -1)
                continue;

            if (invItem instanceof HideSpectatorsInvItem) {
                if (!InventoryManager.SPECTATOR_MODE_ENABLED)
                    continue;

                if (profile.isHideSpectators())
                    continue;
            } else if (invItem instanceof ShowSpectatorsInvItem) {
                if (!InventoryManager.SPECTATOR_MODE_ENABLED)
                    continue;

                if (!profile.isHideSpectators())
                    continue;
            } else if (invItem instanceof RandomMatchInvItem) {
                if (!InventoryManager.SPECTATOR_MODE_ENABLED)
                    continue;
            } else if (invItem instanceof SpecMenuInvItem) {
                if (!InventoryManager.SPECTATOR_MODE_ENABLED)
                    continue;

                if (!InventoryManager.SPECTATOR_MENU_ENABLED)
                    continue;
            }

            playerInventory.setItem(slot, invItem.getItem());
        }
    }

}
