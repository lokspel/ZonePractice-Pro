package dev.nandi0813.practice.Manager.Ladder.Type;

import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.CustomConfig;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.LadderHandle;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.BedFight;
import dev.nandi0813.practice.Manager.Ladder.Enum.LadderType;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import static dev.nandi0813.practice.Util.PermanentConfig.PLACED_IN_FIGHT;

public class BedWars extends BedFight implements CustomConfig, LadderHandle {

    public BedWars(String name, LadderType type) {
        super(name, type);
    }

    @Override
    public void setCustomConfig(YamlConfiguration config) {
        config.set("bed-respawn", this.respawnTime);
        config.set("bedwars-respawn", null);
    }

    @Override
    public void getCustomConfig(YamlConfiguration config) {
        if (config.isInt("bedwars-respawn")) {
            this.respawnTime = config.getInt("bedwars-respawn");
        } else if (config.isInt("bed-respawn")) {
            this.respawnTime = config.getInt("bed-respawn");
        } else
            this.respawnTime = 3;

        if (this.respawnTime < 3 || this.respawnTime > 10)
            this.respawnTime = 3;
    }

    @Override
    public boolean handleEvents(Event e, Match match) {
        if (e instanceof EntityDamageEvent) {
            onPlayerDamage((EntityDamageEvent) e, match);
            return true;
        } else if (e instanceof PlayerDropItemEvent) {
            onItemDrop((PlayerDropItemEvent) e);
            return true;
        } else if (e instanceof PlayerMoveEvent) {
            onPlayerMove((PlayerMoveEvent) e, match);
            return true;
        } else if (e instanceof BlockBreakEvent) {
            onBedDestroy((BlockBreakEvent) e, match);
            return true;
        } else if (e instanceof BlockPlaceEvent) {
            onBlockPlace((BlockPlaceEvent) e, match);
            return true;
        }
        return false;
    }

    private static void onBlockPlace(final @NotNull BlockPlaceEvent e, final @NotNull Match match) {
        Block block = e.getBlockPlaced();
        if (block.getType().equals(Material.TNT)) {
            ClassImport.getClasses().getLadderUtil().placeTnt(e, match);
        } else {
            block.setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(ZonePractice.getInstance(), match));
            match.addBlockChange(ClassImport.createChangeBlock(e));

            Block underBlock = e.getBlockPlaced().getLocation().subtract(0, 1, 0).getBlock();
            if (ClassImport.getClasses().getArenaUtil().turnsToDirt(underBlock))
                match.addBlockChange(ClassImport.createChangeBlock(underBlock));
        }
    }

    private static void onPlayerDamage(final @NotNull EntityDamageEvent e, final Match match) {
        if (!(e.getEntity() instanceof Player player)) return;

        if (!match.getCurrentRound().getRoundStatus().equals(RoundStatus.LIVE)) e.setCancelled(true);
        if (match.getCurrentStat(player).isSet()) return;

        EntityDamageEvent.DamageCause cause = e.getCause();
        if (cause.equals(EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
        }
    }

}
