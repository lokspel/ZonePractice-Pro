package dev.nandi0813.practice.Manager.Fight.Match.Runnable.Round;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Round;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundEndRunnable extends BukkitRunnable {

    private final Match match;
    private final Round round;

    @Getter
    private int seconds;
    @Getter
    private boolean running = false;

    @Getter
    private final boolean ended;

    public RoundEndRunnable(Round round, boolean ended) {
        this.round = round;
        this.match = round.getMatch();
        this.ended = ended;

        if (ended)
            this.seconds = ConfigManager.getConfig().getInt("MATCH-SETTINGS.AFTER-COUNTDOWN");
        else
            this.seconds = 0;
    }

    public RoundEndRunnable begin() {
        this.round.setRoundStatus(RoundStatus.END);
        if (this.ended) match.setStatus(MatchStatus.END);

        running = true;
        this.runTaskTimer(ZonePractice.getInstance(), 0, 20L);

        return this;
    }

    @Override
    public void cancel() {
        if (running) {
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            running = false;
        }

        this.round.setRoundEndRunnable(null);
    }

    @Override
    public void run() {
        if (seconds == 0) {
            this.cancel();

            if (ended)
                match.endMatch();
            else
                match.startNextRound();
        } else
            seconds--;
    }

}
