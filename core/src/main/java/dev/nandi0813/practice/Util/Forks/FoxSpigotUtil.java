package dev.nandi0813.practice.Util.Forks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.foxspigot.jar.knockback.KnockbackModule;
import pt.foxspigot.jar.knockback.KnockbackProfile;

import java.util.ArrayList;
import java.util.List;

public enum FoxSpigotUtil {
    ;

    public static KnockbackProfile getNextKnockbackProfile(KnockbackProfile knockbackProfile) {
        List<KnockbackProfile> list = new ArrayList<>(KnockbackModule.INSTANCE.profiles.values());

        if (knockbackProfile != null) {
            int c = list.indexOf(knockbackProfile);

            if (list.size() - 1 == c)
                return list.get(0);
            else
                return list.get(c + 1);
        } else
            return list.get(0);
    }

    public static void setKnockbackProfile(Player player, KnockbackProfile knockbackProfile) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "knockback set " + knockbackProfile.title + " " + player.getName());
    }

}
