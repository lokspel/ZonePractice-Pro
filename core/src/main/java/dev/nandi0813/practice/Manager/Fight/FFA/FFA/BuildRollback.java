package dev.nandi0813.practice.Manager.Fight.FFA.FFA;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Util.FightMapChange.FightChange;
import dev.nandi0813.practice.Util.Interface.Runnable;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class BuildRollback extends Runnable {

    private static final int ROLLBACK_SECONDS = ConfigManager.getInt("FFA.ROLLBACK.SECONDS");

    private final FightChange fightChange;

    public BuildRollback(FightChange fightChange) {
        super(20L, 20L, false);
        this.fightChange = fightChange;
        this.seconds = ROLLBACK_SECONDS;
    }

    @Override
    public void run() {
        if (seconds == 0) {
            this.rollback();
        }

        seconds--;
    }

    @Override
    public void cancel() {
        if (!running) return;

        running = false;
        Bukkit.getScheduler().cancelTask(this.getTaskId());

        this.rollback();
    }

    public void rollback() {
        this.seconds = ROLLBACK_SECONDS;
        fightChange.rollback(300, 100);
    }

}
