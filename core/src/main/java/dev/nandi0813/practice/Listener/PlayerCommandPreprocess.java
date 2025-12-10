package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler ( ignoreCancelled = true, priority = EventPriority.HIGHEST )
    public void onReloadCommand(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().split(" ")[0].replace("/", "").replaceAll("(?i)bukkit:", "");

        if (cmd.equalsIgnoreCase("reload") || cmd.equalsIgnoreCase("rl")) {
            e.setCancelled(true);
            Common.sendMMMessage(e.getPlayer(), LanguageManager.getString("RELOAD-DISABLED"));
        }
    }

}
