package dev.nandi0813.practice.Manager.Server;

import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.Backend.BackendManager;
import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.HologramManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.NumberUtil;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveRunnable extends BukkitRunnable {

    @Getter
    private boolean running = false;

    private final long interval = ConfigManager.getInt("AUTO-SAVE.INTERVAL") * 60 * 20L;
    private final boolean alert = ConfigManager.getBoolean("AUTO-SAVE.ALERT");

    public void begin() {
        running = true;
        this.runTaskTimerAsynchronously(ZonePractice.getInstance(), interval, interval);
    }

    @Override
    public void run() {
        if (alert) {
            ServerManager.getInstance().alertPlayers("zpp.autosave.alert", LanguageManager.getString("AUTO-SAVE.STARTED"));

            Bukkit.getScheduler().runTaskLaterAsynchronously(ZonePractice.getInstance(), () ->
                    ServerManager.getInstance().alertPlayers("zpp.autosave.alert", LanguageManager.getString("AUTO-SAVE.ENDED")), NumberUtil.getRandomNumber(4, 10) * 20L);
        }

        save();
    }

    public void save() {
        EventManager.getInstance().saveEventData();
        ArenaManager.getInstance().saveArenas();
        LadderManager.getInstance().saveLadders();
        ProfileManager.getInstance().saveProfiles();
        Bukkit.getScheduler().runTask(ZonePractice.getInstance(), () -> HologramManager.getInstance().saveHolograms());
        BackendManager.save();
    }

}
