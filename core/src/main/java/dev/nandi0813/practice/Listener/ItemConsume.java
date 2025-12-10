package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ItemConsume implements Listener {

    @EventHandler ( ignoreCancelled = true )
    public void onConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        Profile profile = ProfileManager.getInstance().getProfile(player);
        ItemStack item = e.getItem();

        switch (profile.getStatus()) {
            case MATCH:
            case FFA:
            case EVENT:
                if (item != null && item.getType() == Material.POTION && ConfigManager.getConfig().getBoolean("MATCH-SETTINGS.REMOVE-EMPTY-BOTTLE"))
                    Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () -> player.getInventory().remove(Material.GLASS_BOTTLE), 1L);
                break;
        }
    }

}
