package dev.nandi0813.practice.Manager.GUI.GUIs.Leaderboard;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.Division.Division;
import dev.nandi0813.practice.Manager.Division.DivisionManager;
import dev.nandi0813.practice.Manager.Division.DivisionUtil;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.GUIs.DivisionGui;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.StatisticUtil;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LbSelectorGui extends GUI {

    private static final ItemStack FILLER_ITEM = GUIFile.getGuiItem("GUIS.STATISTICS.SELECTOR.ICONS.FILLER-ITEM").get();

    private final Player opener;
    private final Profile profile;

    private final LbProfileStatGui lbProfileStatGui;
    private final LbEloGui lbEloGui;
    private final LbWinGui lbWinGui;

    public LbSelectorGui(Player opener, Profile profile) {
        super(GUIType.Leaderboard_Selector);

        this.gui.put(1, InventoryUtil.createInventory(GUIFile.getString("GUIS.STATISTICS.SELECTOR.TITLE"), 4));
        this.opener = opener;
        this.profile = profile;

        this.lbProfileStatGui = new LbProfileStatGui(profile, this);
        this.lbEloGui = new LbEloGui(this);
        this.lbWinGui = new LbWinGui(this);

        build();
    }

    @Override
    public void build() {
        Inventory inventory = gui.get(1);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, FILLER_ITEM);
        }

        update();
    }

    @Override
    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
        {
            Inventory inventory = gui.get(1);

            inventory.setItem(13, LbGuiUtil.createProfileStatItem(profile, opener));
            inventory.setItem(20, GUIFile.getGuiItem("GUIS.STATISTICS.SELECTOR.ICONS.ELO-LEADERBOARD").get());
            inventory.setItem(22, getDivisionItem(profile));
            inventory.setItem(24, GUIFile.getGuiItem("GUIS.STATISTICS.SELECTOR.ICONS.TOP-WIN-LEADERBOARD").get());

            updatePlayers();
        });
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        e.setCancelled(true);

        switch (slot) {
            case 13:
                lbProfileStatGui.open(player);
                break;
            case 20:
                lbEloGui.open(player);
                break;
            case 22:
                new DivisionGui(profile, this).open(player);
                break;
            case 24:
                lbWinGui.open(player);
                break;
        }
    }

    private static ItemStack getDivisionItem(Profile profile) {
        Division division = profile.getStats().getDivision();
        Division nextDivision = DivisionManager.getInstance().getNextDivision(profile);

        GUIItem guiItem;
        if (nextDivision != null) {
            guiItem = GUIFile.getGuiItem("GUIS.STATISTICS.SELECTOR.ICONS.VIEW-DIVISIONS.HAS-NEXT");

            guiItem
                    .replaceAll("%nextDivision_fullName%", Common.mmToNormal(nextDivision.getFullName()))
                    .replaceAll("%nextDivision_shortName%", Common.mmToNormal(nextDivision.getShortName()))
                    .replaceAll("%nextDivision_exp%", String.valueOf(nextDivision.getExperience()))
                    .replaceAll("%nextDivision_wins%", String.valueOf(nextDivision.getWin()))
                    .replaceAll("%progress_bar%", StatisticUtil.getProgressBar(DivisionUtil.getDivisionProgress(profile, nextDivision)))
                    .replaceAll("%progress_percent%", String.valueOf(DivisionUtil.getDivisionProgress(profile, nextDivision)));
        } else {
            guiItem = GUIFile.getGuiItem("GUIS.STATISTICS.SELECTOR.ICONS.VIEW-DIVISIONS.NO-NEXT");
        }

        guiItem
                .replaceAll("%division_fullName%", Common.mmToNormal(division.getFullName()))
                .replaceAll("%division_shortName%", Common.mmToNormal(division.getShortName()))
                .replaceAll("%exp%", String.valueOf(profile.getStats().getExperience()))
                .replaceAll("%elo%", String.valueOf(profile.getStats().getGlobalElo()))
                .replaceAll("%wins%", String.valueOf(profile.getStats().getGlobalWins()));

        return guiItem.get();
    }

}
