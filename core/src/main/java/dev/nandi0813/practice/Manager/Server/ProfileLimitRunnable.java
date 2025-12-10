package dev.nandi0813.practice.Manager.Server;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.WeightClass;
import dev.nandi0813.practice.Manager.Profile.Group.Group;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class ProfileLimitRunnable extends BukkitRunnable {

    public void begin() {
        ZonedDateTime zdt = LocalDate.now(TimeZone.getDefault().toZoneId()).atTime(LocalTime.of(23, 59, 59)).atZone(TimeZone.getDefault().toZoneId());

        long i2 = zdt.toInstant().toEpochMilli() - System.currentTimeMillis();
        long i3 = (i2 / 1000) * 20;

        this.runTaskTimerAsynchronously(ZonePractice.getInstance(), i3, 86400000L);
    }

    @Override
    public void run() {
        for (Profile profile : ProfileManager.getInstance().getProfiles().values()) {
            Group group = profile.getGroup();

            profile.setRankedLeft(group != null ? group.getRankedLimit() : 0);
            profile.setUnrankedLeft(group != null ? group.getUnrankedLimit() : 0);
            profile.setEventStartLeft(group != null ? group.getEventStartLimit() : 0);
        }

        for (Player player : Bukkit.getOnlinePlayers())
            Common.sendMMMessage(player, LanguageManager.getString("GAMES-RESET")
                    .replaceAll("%unranked%", WeightClass.UNRANKED.getMMName())
                    .replaceAll("%ranked%", WeightClass.RANKED.getMMName())
            );
    }

}
