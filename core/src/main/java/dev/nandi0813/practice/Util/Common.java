package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.ZonePractice;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Common {
    ;

    public static void sendMMMessage(Player player, String line) {
        if (line.contains("&") || line.contains("§")) line = StringUtil.legacyColorToMiniMessage(line);

        if (SoftDependUtil.isPAPI_ENABLED) {
            line = PlaceholderAPI.setPlaceholders(player, line);
        }

        ZonePractice.getAdventure().player(player).sendMessage(ZonePractice.getMiniMessage().deserialize(line));
    }

    public static void sendConsoleMMMessage(String string) {
        ZonePractice.getAdventure().console().sendMessage(ZonePractice.getMiniMessage().deserialize(string));
    }


    public static Component deserializeMiniMessage(String line) {
        return ZonePractice.getMiniMessage().deserialize(line);
    }

    public static String serializeComponentToLegacyString(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    public static String mmToNormal(String line) {
        if (line.contains("&") || line.contains("§")) line = StringUtil.legacyColorToMiniMessage(line);

        return StringUtil.CC(serializeComponentToLegacyString(deserializeMiniMessage(line)));
    }

    public static String serializeNormalToMMString(String normalString) {
        String normalized = normalString.replace('&', LegacyComponentSerializer.SECTION_CHAR);
        Component component = LegacyComponentSerializer.legacySection().deserialize(normalized);
        return ZonePractice.getMiniMessage().serialize(component);
    }

    public static List<String> mmToNormal(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String line : list) {
            if (line.contains("&") || line.contains("§")) line = StringUtil.legacyColorToMiniMessage(line);

            newList.add(mmToNormal(line));
        }
        return newList;
    }

}
