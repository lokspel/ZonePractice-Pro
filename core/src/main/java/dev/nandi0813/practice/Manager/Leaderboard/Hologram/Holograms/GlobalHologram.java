package dev.nandi0813.practice.Manager.Leaderboard.Hologram.Holograms;

import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Hologram;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.HologramType;
import dev.nandi0813.practice.Manager.Leaderboard.Leaderboard;
import dev.nandi0813.practice.Manager.Leaderboard.LeaderboardManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class GlobalHologram extends Hologram {

    public GlobalHologram(String name, Location baseLocation) {
        super(name, baseLocation, HologramType.GLOBAL);
    }

    public GlobalHologram(String name) {
        super(name, HologramType.GLOBAL);
    }

    @Override
    public void getAbstractData(YamlConfiguration config) {
    }

    @Override
    public void setAbstractData(YamlConfiguration config) {
    }

    @Override
    public boolean isReadyToEnable() {
        return leaderboardType != null;
    }

    @Override
    public Leaderboard getNextLeaderboard() {
        return LeaderboardManager.getInstance().searchLB(hologramType.getLbMainType(), leaderboardType, null);
    }

}
