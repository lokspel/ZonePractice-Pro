package dev.nandi0813.practice.Manager.Leaderboard.Hologram.Holograms;

import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Hologram;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.HologramType;
import dev.nandi0813.practice.Manager.Leaderboard.Leaderboard;
import dev.nandi0813.practice.Manager.Leaderboard.LeaderboardManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LadderDynamicHologram extends Hologram {

    private List<NormalLadder> ladders;

    public LadderDynamicHologram(String name, Location baseLocation) {
        super(name, baseLocation, HologramType.LADDER_DYNAMIC);
        this.ladders = new ArrayList<>();
    }

    public LadderDynamicHologram(String name) {
        super(name, HologramType.LADDER_DYNAMIC);
    }

    @Override
    public void getAbstractData(YamlConfiguration config) {
        this.ladders = new ArrayList<>();

        if (config.isSet("holograms." + name + ".ladders")) {
            for (String ladderName : config.getStringList("holograms." + name + ".ladders")) {
                NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
                if (ladder != null && ladder.isEnabled()) {
                    ladders.add(ladder);
                }
            }
        }
    }

    @Override
    public void setAbstractData(YamlConfiguration config) {
        if (!ladders.isEmpty()) {
            config.set("holograms." + name + ".ladders", getLadderNames(ladders));
        } else {
            config.set("holograms." + name + ".ladders", null);
        }
    }

    @Override
    public boolean isReadyToEnable() {
        return !ladders.isEmpty() && leaderboardType != null;
    }

    @Override
    public Leaderboard getNextLeaderboard() {
        return LeaderboardManager.getInstance().searchLB(hologramType.getLbMainType(), leaderboardType, this.getNextLadder());
    }

    public Ladder getNextLadder() {
        if (!ladders.isEmpty()) {
            if (currentLB != null) {
                Ladder cL = currentLB.getLadder();
                int current = ladders.indexOf(cL);

                if (ladders.size() - 1 == current) return ladders.get(0);
                else return ladders.get(current + 1);
            } else
                return ladders.get(0);
        } else
            return null;
    }

    private static List<String> getLadderNames(List<NormalLadder> ladders) {
        List<String> names = new ArrayList<>();
        for (Ladder ladder : ladders)
            names.add(ladder.getName());
        return names;
    }

}
