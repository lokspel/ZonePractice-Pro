package dev.nandi0813.practice_modern.Interfaces;

import dev.nandi0813.practice.Manager.Arena.Util.BedLocation;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Interface.Team;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BedUtil extends dev.nandi0813.practice.Module.Interfaces.BedUtil {

    @Override
    public BedLocation getBedLocation(Block block) {
        if (block == null) return null;

        Location bedLoc = block.getLocation();
        Bed bed = (Bed) block.getState().getBlockData();

        if (bed.getPart().equals(Bed.Part.HEAD))
            bedLoc = block.getRelative(bed.getFacing().getOppositeFace()).getLocation();

        return new BedLocation(bedLoc.getWorld(), bedLoc.getX(), bedLoc.getY(), bedLoc.getZ(), bed.getFacing());
    }

    @Override
    public void placeBed(Location loc, BlockFace face) {
        Block bedFoot = loc.getBlock();
        bedFoot.setType(Material.RED_BED);
        Bed bedFootData = (Bed) Bukkit.createBlockData(Material.RED_BED);
        bedFootData.setPart(Bed.Part.FOOT);
        bedFootData.setFacing(face);

        Block bedHead = bedFoot.getRelative(face);
        bedHead.setType(Material.RED_BED);
        Bed bedHeadData = (Bed) Bukkit.createBlockData(Material.RED_BED);
        bedHeadData.setPart(Bed.Part.HEAD);
        bedHeadData.setFacing(face);

        bedFoot.setBlockData(bedFootData, false);
        bedHead.setBlockData(bedHeadData, false);
    }

    @Override
    public boolean onBedBreak(final @NotNull BlockBreakEvent e, final @NotNull Match match) {
        Player player = e.getPlayer();

        if (match.getCurrentStat(player).isSet()) return false;

        final Map<TeamEnum, Boolean> bedStatus = match.getCurrentRound().getBedStatus();
        if (!bedStatus.get(TeamEnum.TEAM1) && !bedStatus.get(TeamEnum.TEAM2)) return false;

        Block bedBlock = e.getBlock();
        if (!bedBlock.getType().toString().contains("_BED")) return false;

        TeamEnum team = ((Team) match).getTeam(player);
        Location bedLoc = bedBlock.getLocation();

        boolean destroy = false;
        if (match.getArena().getBedLoc1().getLocation().equals(bedLoc)
                || match.getArena().getBedLoc1().getLocation().getBlock().getRelative(match.getArena().getBedLoc1().getFacing()).getLocation().equals(bedLoc)) {
            e.setCancelled(true);

            if (team.equals(TeamEnum.TEAM2)) {
                destroy = true;

                if (bedStatus.get(TeamEnum.TEAM1)) {
                    bedStatus.replace(TeamEnum.TEAM1, false);
                    sendBedDestroyMessage(match, TeamEnum.TEAM1);
                }
            } else
                Common.sendMMMessage(player, LanguageManager.getString("MATCH.CANT-BREAK-OWN-BED"));
        } else if (match.getArena().getBedLoc2().getLocation().equals(bedLoc)
                || match.getArena().getBedLoc2().getLocation().getBlock().getRelative(match.getArena().getBedLoc2().getFacing()).getLocation().equals(bedLoc)) {
            e.setCancelled(true);

            if (team.equals(TeamEnum.TEAM1)) {
                destroy = true;

                if (bedStatus.get(TeamEnum.TEAM2)) {
                    bedStatus.replace(TeamEnum.TEAM2, false);
                    sendBedDestroyMessage(match, TeamEnum.TEAM2);
                }
            } else
                Common.sendMMMessage(player, LanguageManager.getString("MATCH.CANT-BREAK-OWN-BED"));
        }

        if (destroy) {
            BedLocation bedLocation = getBedLocation(e.getBlock());
            Block head = bedLocation.getBlock().getRelative(bedLocation.getFacing());

            match.addBlockChange(ClassImport.createChangeBlock(bedLocation.getBlock()));

            head.setType(Material.AIR);
            bedLocation.getBlock().setType(Material.AIR);
        }

        return destroy;
    }

}
