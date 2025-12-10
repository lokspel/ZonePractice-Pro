package dev.nandi0813.practice.Util.Cooldown;

import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Fight.Util.Stats.Statistic;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class GoldenAppleRunnable extends BukkitRunnable {

    private final Player player;
    private final Profile profile;
    private boolean running;
    private final int seconds;

    public GoldenAppleRunnable(Player player, int seconds) {
        this.player = player;
        this.seconds = seconds;
        profile = ProfileManager.getInstance().getProfile(player);
    }

    public void begin() {
        running = true;
        PlayerCooldown.addCooldown(player, CooldownObject.GOLDEN_APPLE, seconds);
        this.runTaskTimerAsynchronously(ZonePractice.getInstance(), 0, 10L);
    }

    @Override
    public void cancel() {
        if (running) {
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            running = false;
            PlayerCooldown.removeCooldown(player, CooldownObject.GOLDEN_APPLE);
        }
    }

    @Override
    public void run() {
        if (PlayerCooldown.isActive(player, CooldownObject.GOLDEN_APPLE)) {
            if (profile.getStatus().equals(ProfileStatus.MATCH) || profile.getStatus().equals(ProfileStatus.FFA)) {
                Statistic roundStatistic = MatchManager.getInstance().getLiveMatchByPlayer(player).getCurrentStat(player);

                if (roundStatistic.isSet())
                    cancel();
            } else
                cancel();
        } else
            cancel();
    }
}
