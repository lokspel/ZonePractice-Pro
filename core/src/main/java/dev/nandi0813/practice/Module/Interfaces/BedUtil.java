package dev.nandi0813.practice.Module.Interfaces;

import dev.nandi0813.practice.Manager.Arena.Util.BedLocation;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BedUtil implements Listener {

    public abstract boolean onBedBreak(final @NotNull BlockBreakEvent e, final @NotNull Match match);

    public abstract BedLocation getBedLocation(Block block);

    public abstract void placeBed(Location loc, BlockFace face);

    protected void sendBedDestroyMessage(Match match, TeamEnum team) {
        String languagePath = switch (match.getLadder().getType()) {
            case BEDWARS -> "MATCH." + match.getType().getPathName() + ".LADDER-SPECIFIC.BED-WARS";
            case FIREBALL_FIGHT -> "MATCH." + match.getType().getPathName() + ".LADDER-SPECIFIC.FIREBALL-FIGHT";
            default -> null;
        };

        if (languagePath == null) return;

        match.sendMessage(LanguageManager.getString(languagePath + ".BED-DESTROYED")
                        .replaceAll("%team%", team.getNameMM())
                        .replaceAll("%teamColor%", team.getColorMM())
                , true);
    }

}
