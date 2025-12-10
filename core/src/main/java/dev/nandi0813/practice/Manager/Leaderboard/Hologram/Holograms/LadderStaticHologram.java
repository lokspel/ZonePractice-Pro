package dev.nandi0813.practice.Manager.Leaderboard.Hologram.Holograms;

import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Hologram;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.HologramType;
import dev.nandi0813.practice.Manager.Leaderboard.Leaderboard;
import dev.nandi0813.practice.Manager.Leaderboard.LeaderboardManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
@Setter
public class LadderStaticHologram extends Hologram {

    private NormalLadder ladder;

    public LadderStaticHologram(String name, Location baseLocation) {
        super(name, baseLocation, HologramType.LADDER_STATIC);
    }

    public LadderStaticHologram(String name) {
        super(name, HologramType.LADDER_STATIC);
    }

    @Override
    public void getAbstractData(YamlConfiguration config) {
        if (config.isSet("holograms." + name + ".ladder")) {
            NormalLadder ladder = LadderManager.getInstance().getLadder(config.getString("holograms." + name + ".ladder"));
            if (ladder != null && ladder.isEnabled())
                this.ladder = ladder;
        }
    }

    @Override
    public void setAbstractData(YamlConfiguration config) {
        if (ladder != null) {
            config.set("holograms." + name + ".ladder", ladder.getName());
        } else {
            config.set("holograms." + name + ".ladder", null);
        }
    }

    @Override
    public boolean isReadyToEnable() {
        return ladder != null && leaderboardType != null;
    }

    @Override
    public Leaderboard getNextLeaderboard() {
        return LeaderboardManager.getInstance().searchLB(hologramType.getLbMainType(), leaderboardType, ladder);
    }

}
