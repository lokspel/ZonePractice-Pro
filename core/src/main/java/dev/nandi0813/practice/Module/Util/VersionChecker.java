package dev.nandi0813.practice.Module.Util;

import lombok.Getter;
import org.bukkit.Bukkit;

public enum VersionChecker {
    ;

    private static BukkitVersion bukkitVersion;

    public static BukkitVersion getBukkitVersion() {
        if (bukkitVersion == null) {
            final String version = Bukkit.getVersion();
            if (version.contains("(MC: 1.8.8)") || version.contains("(MC: 1.8.9)"))
                bukkitVersion = BukkitVersion.v1_8_R3;
            else if (version.contains("(MC: 1.20.6)"))
                bukkitVersion = BukkitVersion.v1_20_R4;
            else if (version.contains("(MC: 1.21)") || version.contains("(MC: 1.21.1)") || version.contains("(MC: 1.21.2)") || version.contains("(MC: 1.21.3)") || version.contains("(MC: 1.21.4)"))
                bukkitVersion = BukkitVersion.v1_21_R3;
            else
                bukkitVersion = null;
        }
        return bukkitVersion;
    }

    @Getter
    public enum BukkitVersion {
        v1_8_R3("1_8_8", "1.8.8/", false), // 1.8.8
        v1_20_R4("modern", "modern/", true), // 1.20.6
        v1_21_R3("modern", "modern/", true); // 1.21.4

        private final String moduleVersionExtension;
        private final String filePath;
        private final boolean secondHand;

        BukkitVersion(final String moduleVersionExtension, final String filePath, final boolean secondHand) {
            this.moduleVersionExtension = moduleVersionExtension;
            this.filePath = filePath;
            this.secondHand = secondHand;
        }
    }

}
