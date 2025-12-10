package dev.nandi0813.practice_modern;

import dev.nandi0813.practice.Module.Interfaces.*;
import dev.nandi0813.practice.ZonePractice;
import dev.nandi0813.practice_modern.Interfaces.MatchTntListener;
import dev.nandi0813.practice_modern.Interfaces.PlayerHiderUtil;
import dev.nandi0813.practice_modern.Listener.ArenaListener;
import dev.nandi0813.practice_modern.Listener.EPCountdownListener;
import dev.nandi0813.practice_modern.Listener.FFAListener;
import dev.nandi0813.practice_modern.Listener.MatchListener;
import dev.nandi0813.practice_modern.New.BlockExplode;
import dev.nandi0813.practice_modern.New.ItemOffHand;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class Classes implements dev.nandi0813.practice.Module.Util.Classes {

    public WorldCreate worldCreate = new dev.nandi0813.practice_modern.Interfaces.WorldCreate();
    public PlayerHiderInterface playerHiderUtil = new PlayerHiderUtil();
    public ItemMaterialUtil itemMaterialUtil = new dev.nandi0813.practice_modern.Interfaces.ItemMaterialUtil();
    public LadderUtil ladderUtil = new dev.nandi0813.practice_modern.Interfaces.LadderUtil();
    public ItemCreateUtil itemCreateUtil = new dev.nandi0813.practice_modern.Interfaces.ItemCreateUtil();
    public PlayerUtil playerUtil = new dev.nandi0813.practice_modern.Interfaces.PlayerUtil();
    public ArenaUtil arenaUtil = new dev.nandi0813.practice_modern.Interfaces.ArenaUtil();
    public ArenaCopyUtil arenaCopyUtil = new dev.nandi0813.practice_modern.Interfaces.ArenaCopy.ArenaCopyUtil();

    public BedUtil bedUtil = new dev.nandi0813.practice_modern.Interfaces.BedUtil();
    public EntityHider entityHider = new dev.nandi0813.practice_modern.Interfaces.EntityHider(ZonePractice.getInstance(), EntityHider.Policy.BLACKLIST);
    public StatisticListener statisticListener = new dev.nandi0813.practice_modern.Interfaces.StatisticListener();

    public Class<?> changedBlockClass = dev.nandi0813.practice_modern.Interfaces.ChangedBlock.class;
    public Class<?> kitDataClass = dev.nandi0813.practice_modern.Interfaces.KitData.class;
    public Class<?> actionBarClass = dev.nandi0813.practice_modern.Interfaces.ActionBar.class;

    public Classes() {
        Bukkit.getServer().getPluginManager().registerEvents(arenaCopyUtil, ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(statisticListener, ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new MatchListener(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new FFAListener(), ZonePractice.getInstance());

        // Only 1.20 stuff
        Bukkit.getServer().getPluginManager().registerEvents(new ItemOffHand(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new BlockExplode(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new MatchTntListener(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new ArenaListener(), ZonePractice.getInstance());

        Bukkit.getServer().getPluginManager().registerEvents(new EPCountdownListener(), ZonePractice.getInstance());
    }

}
