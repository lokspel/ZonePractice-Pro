package dev.nandi0813.practice.Manager.Backend;

import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.StringUtil;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum GUIFile {
    ;

    private static File file;
    @Getter
    private static YamlConfiguration config;

    public static void createFile(ZonePractice practice) {
        file = new File(practice.getDataFolder(), "guis.yml");
        config = new YamlConfiguration();
        reload();
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Common.sendConsoleMMMessage("<red>Error: " + e.getMessage());
        }
    }

    public static void reload() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Common.sendConsoleMMMessage("<red>Error: " + e.getMessage());
        }
    }

    public static String getString(String loc) {
        return StringUtil.CC(config.getString(loc.toUpperCase()));
    }

    public static int getInt(String loc) {
        return config.getInt(loc.toUpperCase());
    }

    public static List<String> getStringList(String loc) {
        return StringUtil.CC(config.getStringList(loc.toUpperCase()));
    }

    public static GUIItem getGuiItem(String loc) {
        return BackendUtil.getGuiItem(config, loc);
    }

}
