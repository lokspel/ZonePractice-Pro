package dev.nandi0813.practice.Util.Forks;

import org.bukkit.Bukkit;
import pt.foxspigot.jar.knockback.KnockbackModule;
import xyz.refinedev.spigot.api.knockback.KnockbackAPI;

public enum ForkUtil {
    ;

    public static boolean isCarbon() throws NoClassDefFoundError {
        try {
            return Bukkit.getServer().getVersion().contains("Carbon") && KnockbackAPI.getInstance() != null;
        } catch (Exception e) {
            throw new NoClassDefFoundError();
        }
    }

    public static boolean isFoxSpigot() throws NoClassDefFoundError {
        try {
            return KnockbackModule.INSTANCE != null;
        } catch (Exception e) {
            throw new NoClassDefFoundError();
        }
    }

}
