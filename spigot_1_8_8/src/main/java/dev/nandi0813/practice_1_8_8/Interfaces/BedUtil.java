package dev.nandi0813.practice_1_8_8.Interfaces;

import dev.nandi0813.practice.Manager.Arena.Util.BedLocation;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Interface.Team;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Bed;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BedUtil extends dev.nandi0813.practice.Module.Interfaces.BedUtil {

    @Override
    public BedLocation getBedLocation(Block block) {
        if (block == null) return null;

        Location bedLoc = block.getLocation();
        Bed bed = (Bed) block.getState().getData();

        if (bed.isHeadOfBed())
            bedLoc = block.getRelative(bed.getFacing().getOppositeFace()).getLocation();

        return new BedLocation(bedLoc.getWorld(), bedLoc.getX(), bedLoc.getY(), bedLoc.getZ(), bed.getFacing());
    }

    @Override
    public void placeBed(Location loc, BlockFace face) {
        Block bedFootBlock = loc.getBlock();
        Block bedHeadBlock = bedFootBlock.getRelative(face);

        BlockState bedFootState = bedFootBlock.getState();
        bedFootState.setType(Material.BED_BLOCK);
        Bed bedFootData = new Bed(Material.BED_BLOCK);
        bedFootData.setHeadOfBed(false);
        bedFootData.setFacingDirection(face);
        bedFootState.setData(bedFootData);
        bedFootState.update(true);

        BlockState bedHeadState = bedHeadBlock.getState();
        bedHeadState.setType(Material.BED_BLOCK);
        Bed bedHeadData = new Bed(Material.BED_BLOCK);
        bedHeadData.setHeadOfBed(true);
        bedHeadData.setFacingDirection(face);
        bedHeadState.setData(bedHeadData);
        bedHeadState.update(true);
    }

    @Override
    public boolean onBedBreak(final @NotNull BlockBreakEvent e, final @NotNull Match match) {
        Player player = e.getPlayer();

        if (match.getCurrentStat(player).isSet()) return false;

        final Map<TeamEnum, Boolean> bedStatus = match.getCurrentRound().getBedStatus();
        if (!bedStatus.get(TeamEnum.TEAM1) && !bedStatus.get(TeamEnum.TEAM2)) return false;

        Block bedBlock = e.getBlock();
        if (bedBlock == null || !bedBlock.getType().equals(Material.BED_BLOCK)) return false;

        TeamEnum team = ((Team) match).getTeam(player);
        Location bedLoc = bedBlock.getLocation();
        Bed bed = (Bed) e.getBlock().getState().getData();

        boolean destroy = false;
        if (match.getArena().getBedLoc1().getLocation().equals(bedLoc)
                || match.getArena().getBedLoc1().getLocation().getBlock().getRelative(match.getArena().getBedLoc1().getFacing()).getLocation().equals(bedLoc)) {
            e.setCancelled(true);

            if (team.equals(TeamEnum.TEAM2)) {
                destroy = true;

                bedStatus.replace(TeamEnum.TEAM1, false);
                sendBedDestroyMessage(match, TeamEnum.TEAM1);
            } else
                Common.sendMMMessage(player, LanguageManager.getString("MATCH.CANT-BREAK-OWN-BED"));
        } else if (match.getArena().getBedLoc2().getLocation().equals(bedLoc)
                || match.getArena().getBedLoc2().getLocation().getBlock().getRelative(match.getArena().getBedLoc2().getFacing()).getLocation().equals(bedLoc)) {
            e.setCancelled(true);

            if (team.equals(TeamEnum.TEAM1)) {
                destroy = true;

                bedStatus.replace(TeamEnum.TEAM2, false);
                sendBedDestroyMessage(match, TeamEnum.TEAM2);
            } else
                Common.sendMMMessage(player, LanguageManager.getString("MATCH.CANT-BREAK-OWN-BED"));
        }

        if (destroy) {
            match.addBlockChange(ClassImport.createChangeBlock(e.getBlock()));
            if (bed.isHeadOfBed())
                bedLoc.getBlock().getRelative(bed.getFacing().getOppositeFace()).setType(Material.AIR);
            else
                bedBlock.setType(Material.AIR);
        }

        return destroy;
    }

}
