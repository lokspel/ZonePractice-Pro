package dev.nandi0813.practice.Util.Cooldown;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventStatus;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
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
public class EnderpearlRunnable extends BukkitRunnable {

    private final Player player;
    private final Profile profile;
    private boolean running;
    private final int seconds;

    public EnderpearlRunnable(Player player, int seconds) {
        this.player = player;
        this.seconds = seconds;
        profile = ProfileManager.getInstance().getProfile(player);
    }

    public void begin() {
        running = true;
        PlayerCooldown.addCooldown(player, CooldownObject.ENDER_PEARL, seconds);
        this.runTaskTimerAsynchronously(ZonePractice.getInstance(), 0, 1L);
    }

    @Override
    public void cancel() {
        if (running) {
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            running = false;
            player.setExp(0);
            player.setLevel(0);
            PlayerCooldown.removeCooldown(player, CooldownObject.ENDER_PEARL);
        }
    }

    @Override
    public void run() {
        if (PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL)) {
            if (profile.getStatus().equals(ProfileStatus.MATCH) || profile.getStatus().equals(ProfileStatus.FFA)) {
                Match match = MatchManager.getInstance().getLiveMatchByPlayer(player);
                Statistic roundStatistic = match.getCurrentStat(player);

                if (roundStatistic.isSet())
                    cancel();
                else {
                    if (ConfigManager.getBoolean("MATCH-SETTINGS.ENDERPEARL.EXP-BAR")) {
                        int level = (int) (PlayerCooldown.getLeft(player, CooldownObject.ENDER_PEARL) / 1000);
                        player.setLevel(level);

                        float exp = ((PlayerCooldown.getLeft(player, CooldownObject.ENDER_PEARL) / (float) seconds) / 1000);
                        player.setExp(exp);
                    }
                }
            } else if (profile.getStatus().equals(ProfileStatus.EVENT)) {
                Event event = EventManager.getInstance().getEventByPlayer(player);
                if (event == null) {
                    cancel();
                    return;
                }

                EventStatus eventStatus = event.getStatus();
                if (eventStatus.equals(EventStatus.END) || eventStatus.equals(EventStatus.START)) {
                    cancel();
                }
            } else
                cancel();
        } else
            cancel();
    }

}
