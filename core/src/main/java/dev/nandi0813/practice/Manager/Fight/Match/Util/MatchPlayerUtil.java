package dev.nandi0813.practice.Manager.Fight.Match.Util;

import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.EntityHider.PlayerHider;
import dev.nandi0813.practice.Util.PlayerUtil.PlayerUtil;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public enum MatchPlayerUtil {
    ;

    public static void hidePlayerPartyGames(Player hider, List<Player> matchPlayers) {
        for (Player matchPlayer : matchPlayers) {
            if (!matchPlayer.equals(hider))
                PlayerHider.getInstance().hidePlayer(matchPlayer, hider, false);
        }

        PlayerUtil.setFightPlayer(hider);

        ClassImport.getClasses().getPlayerUtil().setCollidesWithEntities(hider, false);
        Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () ->
        {
            hider.setAllowFlight(true);
            hider.setFlying(true);
            hider.setFireTicks(0);
        }, 2L);
    }

}
