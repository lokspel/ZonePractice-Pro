package dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal;

import dev.nandi0813.practice.Manager.Arena.Arenas.Interface.BasicArena;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Util.TempKillPlayer;
import dev.nandi0813.practice.Manager.Fight.Util.DeathCause;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.TempDead;
import dev.nandi0813.practice.Manager.Ladder.Enum.LadderType;
import dev.nandi0813.practice.Manager.Server.Sound.SoundManager;
import dev.nandi0813.practice.Manager.Server.Sound.SoundType;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Cuboid;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
public abstract class BedFight extends NormalLadder implements TempDead {

    protected int respawnTime;

    protected BedFight(String name, LadderType type) {
        super(name, type);
        this.startMove = false;
    }

    protected static void onItemDrop(final @NotNull PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    protected static void onPlayerMove(final @NotNull PlayerMoveEvent e, final @NotNull Match match) {
        RoundStatus roundStatus = match.getCurrentRound().getRoundStatus();

        if (roundStatus.equals(RoundStatus.LIVE)) {
            Player player = e.getPlayer();
            BasicArena arena = match.getArena();
            Cuboid cuboid = arena.getCuboid();

            TempKillPlayer tempKillPlayer = match.getCurrentRound().getTempKill(player);
            if (tempKillPlayer != null && !cuboid.contains(e.getTo())) {
                player.teleport(cuboid.getCenter());
                return;
            }

            int deadZone = cuboid.getLowerY();
            if (arena.isDeadZone())
                deadZone = arena.getDeadZoneValue();

            if (e.getTo().getBlockY() <= deadZone && tempKillPlayer == null) {
                match.killPlayer(player, null, DeathCause.VOID.getMessage());
            }
        }
    }

    protected static void onBedDestroy(final @NotNull BlockBreakEvent e, final @NotNull Match match) {
        if (ClassImport.getClasses().getBedUtil().onBedBreak(e, match)) {
            SoundManager.getInstance().getSound(SoundType.BED_BREAK).play(match.getPlayers());
        }
    }

}
