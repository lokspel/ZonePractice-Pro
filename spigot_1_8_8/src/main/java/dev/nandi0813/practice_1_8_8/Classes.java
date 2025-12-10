package dev.nandi0813.practice_1_8_8;

import dev.nandi0813.practice.Module.Interfaces.*;
import dev.nandi0813.practice.ZonePractice;
import dev.nandi0813.practice_1_8_8.Interfaces.LadderUtil;
import dev.nandi0813.practice_1_8_8.Interfaces.MatchTntListener;
import dev.nandi0813.practice_1_8_8.Interfaces.PlayerHiderUtil;
import dev.nandi0813.practice_1_8_8.Listener.ArenaListener;
import dev.nandi0813.practice_1_8_8.Listener.FFAListener;
import dev.nandi0813.practice_1_8_8.Listener.MatchListener;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class Classes implements dev.nandi0813.practice.Module.Util.Classes {

    public WorldCreate worldCreate = new dev.nandi0813.practice_1_8_8.Interfaces.WorldCreate();
    public PlayerHiderInterface playerHiderUtil = new PlayerHiderUtil();
    public ItemMaterialUtil itemMaterialUtil = new dev.nandi0813.practice_1_8_8.Interfaces.ItemMaterialUtil();
    public LadderUtil ladderUtil = new LadderUtil();
    public ItemCreateUtil itemCreateUtil = new dev.nandi0813.practice_1_8_8.Interfaces.ItemCreateUtil();
    public PlayerUtil playerUtil = new dev.nandi0813.practice_1_8_8.Interfaces.PlayerUtil();
    public ArenaUtil arenaUtil = new dev.nandi0813.practice_1_8_8.Interfaces.ArenaUtil();
    public ArenaCopyUtil arenaCopyUtil = new dev.nandi0813.practice_1_8_8.Interfaces.ArenaCopyUtil();

    public BedUtil bedUtil = new dev.nandi0813.practice_1_8_8.Interfaces.BedUtil();
    public EntityHider entityHider = new dev.nandi0813.practice_1_8_8.Interfaces.EntityHider(ZonePractice.getInstance(), EntityHider.Policy.BLACKLIST);
    public StatisticListener statisticListener = new dev.nandi0813.practice_1_8_8.Interfaces.StatisticListener();

    public Class<?> changedBlockClass = dev.nandi0813.practice_1_8_8.Interfaces.ChangedBlock.class;
    public Class<?> kitDataClass = dev.nandi0813.practice_1_8_8.Interfaces.KitData.class;
    public Class<?> actionBarClass = dev.nandi0813.practice_1_8_8.Interfaces.ActionBar.class;

    public Classes() {
        Bukkit.getServer().getPluginManager().registerEvents(arenaCopyUtil, ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(statisticListener, ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new MatchListener(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new FFAListener(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new MatchTntListener(), ZonePractice.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new ArenaListener(), ZonePractice.getInstance());

        Bukkit.getServer().getPluginManager().registerEvents(new EPCountdownListener(), ZonePractice.getInstance());
    }

}
