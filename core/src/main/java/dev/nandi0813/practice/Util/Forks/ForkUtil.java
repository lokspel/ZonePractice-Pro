package dev.nandi0813.practice.Util.Forks;

import org.bukkit.Bukkit;
import pt.foxspigot.jar.knockback.KnockbackModule;
import xyz.refinedev.spigot.api.knockback.KnockbackAPI;

public enum ForkUtil {
    ;

    public static boolean isCarbon() throws ClassNotFoundException, NoClassDefFoundError {
        return Bukkit.getServer().getVersion().contains("Carbon") && KnockbackAPI.getInstance() != null;
    }

    public static boolean isFoxSpigot() throws ClassNotFoundException, NoClassDefFoundError {
        return KnockbackModule.INSTANCE != null;
    }

}
