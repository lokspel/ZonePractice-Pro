package dev.nandi0813.practice.Manager.Fight.Util;

import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.FFA.FFAManager;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Util.Interface.Spectatable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public enum FightUtil {
    ;

    public static @Nullable Player getKiller(Entity entity) {
        Player killer = null;
        if (entity instanceof Player) {
            killer = (Player) entity;
        } else if (entity instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player) {
                killer = (Player) arrow.getShooter();
            }
        } else if (entity instanceof Fireball fireball) {
            if (fireball.getShooter() instanceof Player) {
                killer = (Player) fireball.getShooter();
            }
        }
        return killer;
    }

    public static List<Spectatable> getSpectatables() {
        List<Spectatable> spectatables = new ArrayList<>();
        spectatables.addAll(MatchManager.getInstance().getLiveMatches());
        spectatables.addAll(FFAManager.getInstance().getOpenFFAs());
        spectatables.addAll(EventManager.getInstance().getEvents());
        return spectatables;
    }

}
