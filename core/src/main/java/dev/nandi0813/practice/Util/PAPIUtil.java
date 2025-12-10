package dev.nandi0813.practice.Util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public enum PAPIUtil {
    ;

    public static Component runThroughFormat(Player player, String line) {
        if (line == null || line.isEmpty()) {
            return Component.empty();
        }

        if (SoftDependUtil.isPAPI_ENABLED) {
            return MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, line));
        }

        return MiniMessage.miniMessage().deserialize(line);
    }

}
