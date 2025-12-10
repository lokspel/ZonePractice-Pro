package dev.nandi0813.practice_modern.Listener;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Fight.FFA.FFAManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Fight.Util.Runnable.EnderPearlRunnable;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import dev.nandi0813.practice.Util.Cooldown.PlayerCooldown;
import dev.nandi0813.practice.Util.PermanentConfig;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class EPCountdownListener implements Listener {

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof EnderPearl) {
            if (e.getEntity().getShooter() instanceof Player player) {
                FFA ffa = FFAManager.getInstance().getFFAByPlayer(player);
                if (ffa != null) {
                    int duration = ffa.getPlayers().get(player).getEnderPearlCooldown();
                    if (duration <= 0) {
                        return;
                    }

                    if (PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL)) {
                        Common.sendMMMessage(player, StringUtil.replaceSecondString(LanguageManager.getString("FFA.GAME.COOLDOWN.ENDER-PEARL"), PlayerCooldown.getLeftInDouble(player, CooldownObject.ENDER_PEARL)));

                        e.setCancelled(true);
                    } else {
                        EnderPearlRunnable enderPearlCountdown = new EnderPearlRunnable(player, ffa.getFightPlayers().get(player), duration, PermanentConfig.FFA_EXP_BAR);
                        enderPearlCountdown.begin();
                    }

                    return;
                }

                Match match = MatchManager.getInstance().getLiveMatchByPlayer(player);
                if (match != null) {
                    int duration = match.getLadder().getEnderPearlCooldown();
                    if (duration <= 0) {
                        return;
                    }

                    if (!match.getCurrentRound().getRoundStatus().equals(RoundStatus.LIVE)) {
                        e.setCancelled(true);
                        return;
                    }

                    if (PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL)) {
                        Common.sendMMMessage(player, StringUtil.replaceSecondString(LanguageManager.getString("MATCH.COOLDOWN.ENDER-PEARL"), PlayerCooldown.getLeftInDouble(player, CooldownObject.ENDER_PEARL)));

                        e.setCancelled(true);
                    } else {
                        EnderPearlRunnable enderPearlCountdown = new EnderPearlRunnable(player, match.getMatchPlayers().get(player), duration, PermanentConfig.MATCH_EXP_BAR);
                        enderPearlCountdown.begin();
                    }
                }
            }
        }
    }

}
