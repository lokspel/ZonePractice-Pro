package dev.nandi0813.practice.Manager.Fight.Match.Runnable.Round;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Round;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Server.Sound.SoundManager;
import dev.nandi0813.practice.Manager.Server.Sound.SoundType;
import dev.nandi0813.practice.Manager.Spectator.SpectatorManager;
import dev.nandi0813.practice.Util.StringUtil;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundStartRunnable extends BukkitRunnable {

    private final Match match;
    private final Round round;

    @Getter
    private int seconds;
    @Getter
    private boolean running = false;

    public RoundStartRunnable(Round round) {
        this.round = round;
        this.match = round.getMatch();

        Ladder ladder = match.getLadder();

        if (this.round.getRoundNumber() == 1 || ladder.isMultiRoundStartCountdown())
            this.seconds = ladder.getStartCountdown();
        else
            this.seconds = 0;
    }

    @Override
    public void run() {
        String path = "MATCH." + match.getType().getPathName();
        String message;

        if (this.seconds == 0) {
            this.cancel();

            this.round.setRoundStatus(RoundStatus.LIVE);
            this.round.beginRunnable();

            if (this.round.getRoundNumber() == 1) {
                message = StringUtil.replaceSecondString(LanguageManager.getString(path + ".START.MATCH-STARTED"), seconds);
                this.match.setStatus(MatchStatus.LIVE);
                SpectatorManager.getInstance().getSpectatorMenuGui().update();
            } else
                message = StringUtil.replaceSecondString(LanguageManager.getString(path + ".START.ROUND-STARTED"), seconds);

            SoundManager.getInstance().getSound(SoundType.MATCH_STARTED).play(match.getPeople());
        } else {
            if (round.getRoundNumber() == 1)
                message = StringUtil.replaceSecondString(LanguageManager.getString(path + ".START.MATCH-STARTING"), seconds);
            else
                message = StringUtil.replaceSecondString(LanguageManager.getString(path + ".START.ROUND-STARTING"), seconds);

            this.seconds--;

            SoundManager.getInstance().getSound(SoundType.MATCH_START_COUNTDOWN).play(match.getPeople());
        }

        this.match.sendMessage(message, true);
    }

    public RoundStartRunnable begin() {
        this.round.setRoundStatus(RoundStatus.START);

        running = true;
        this.runTaskTimer(
                ZonePractice.getInstance(),
                match.getLadder().isMultiRoundStartCountdown() || round.getRoundNumber() == 1 ? 20L : 0,
                20L);

        return this;
    }

    @Override
    public synchronized void cancel() {
        if (!running) return;

        Bukkit.getScheduler().cancelTask(this.getTaskId());
        running = false;
    }

}
