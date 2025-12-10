package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BroadcastTask extends BukkitRunnable {

    private final Party party;
    @Getter
    private boolean running = false;
    @Getter
    private int count;

    public BroadcastTask(Party party) {
        this.party = party;
    }

    public void begin() {
        count = 0;
        running = true;
        party.setBroadcastParty(true);

        this.runTaskTimerAsynchronously(ZonePractice.getInstance(), 0, ConfigManager.getInt("PARTY.PUBLIC-BROADCAST-TIME") * 20L);
    }

    @Override
    public void cancel() {
        if (running) {
            running = false;
            party.setBroadcastParty(false);

            Bukkit.getScheduler().cancelTask(this.getTaskId());
            party.setBroadcastTask(new BroadcastTask(party));
        }
    }

    @Override
    public void run() {
        if (count > ConfigManager.getInt("PARTY.BROADCAST-TIMES")) {
            cancel();
            return;
        }

        String broadcastMSG = LanguageManager.getString("PARTY.BROADCAST-MSG").replaceAll("%player%", party.getLeader().getName());

        for (Player online : Bukkit.getOnlinePlayers()) {
            Profile profile = ProfileManager.getInstance().getProfile(online);
            switch (profile.getStatus()) {
                case MATCH:
                case FFA:
                case EVENT:
                    continue;
            }

            Party playerParty = PartyManager.getInstance().getParty(online);

            if (playerParty != null && playerParty.equals(party) && !playerParty.getLeader().equals(online))
                return;

            Common.sendMMMessage(online, broadcastMSG);
        }

        count++;
    }

}
