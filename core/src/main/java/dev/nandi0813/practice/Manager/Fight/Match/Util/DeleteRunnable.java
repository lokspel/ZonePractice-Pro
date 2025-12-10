package dev.nandi0813.practice.Manager.Fight.Match.Util;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;

public enum DeleteRunnable {
    ;

    public static void start(Match match) {
        Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () ->
                        MatchManager.getInstance().getMatches().remove(match.getId()),
                20L * ConfigManager.getInt("MATCH-SETTINGS.MATCH-STATISTIC.REMOVE-AFTER"));
    }

}
