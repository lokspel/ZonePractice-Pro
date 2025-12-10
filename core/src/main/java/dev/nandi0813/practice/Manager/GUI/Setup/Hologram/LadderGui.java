package dev.nandi0813.practice.Manager.GUI.Setup.Hologram;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Hologram;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Holograms.LadderDynamicHologram;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.Holograms.LadderStaticHologram;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.InventoryUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LadderGui extends GUI {

    private final Map<Integer, String> ladderSlots = new HashMap<>();
    private final Hologram hologram;

    public LadderGui(Hologram hologram) {
        super(GUIType.Hologram_Ladder);
        this.gui.put(1, InventoryUtil.createInventory(GUIFile.getString("GUIS.SETUP.HOLOGRAM.HOLOGRAM-LADDERS.TITLE").replace("%hologram%", hologram.getName()), 6));
        this.hologram = hologram;

        build();
    }

    @Override
    public void build() {
        Inventory inventory = gui.get(1);

        for (int i = 45; i < 54; i++)
            inventory.setItem(i, GUIManager.getFILLER_ITEM());

        // Navigation item
        gui.get(1).setItem(45, GUIFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-LADDERS.ICONS.GO-BACK").get());

        update();
    }

    @Override
    public void update() {
        if (hologram.getHologramType() == null) return;

        ladderSlots.clear();
        for (int i = 0; i < 45; i++)
            gui.get(1).setItem(i, null);

        for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
            if (ladder.isEnabled() && ladder.getMatchTypes().contains(MatchType.DUEL)) {
                ItemStack ladderItem = null;
                if (hologram instanceof LadderStaticHologram staticHologram) {

                    if (staticHologram.getLadder() != null && staticHologram.getLadder() == ladder)
                        ladderItem = getLadderItem(ladder, true);
                    else
                        ladderItem = getLadderItem(ladder, false);
                } else if (hologram instanceof LadderDynamicHologram dynamicHologram) {

                    if (dynamicHologram.getLeaderboardType().isRankedRelated() && !ladder.isRanked())
                        continue;

                    if (dynamicHologram.getLadders().contains(ladder))
                        ladderItem = getLadderItem(ladder, true);
                    else
                        ladderItem = getLadderItem(ladder, false);
                }

                if (ladderItem != null) {
                    int slot = gui.get(1).firstEmpty();

                    gui.get(1).setItem(slot, ladderItem);
                    ladderSlots.put(slot, ladder.getName());
                }
            }
        }

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getView().getTopInventory();

        int slot = e.getRawSlot();
        ItemStack currentItem = e.getCurrentItem();

        e.setCancelled(true);

        if (inventory.getSize() > slot && currentItem != null && !currentItem.equals(GUIManager.getFILLER_ITEM())) {
            if (slot == 45) {
                HologramSetupManager.getInstance().getHologramSetupGUIs().get(hologram).get(GUIType.Hologram_Main).open(player);
            } else if (ladderSlots.containsKey(slot)) {
                if (!hologram.isEnabled()) {
                    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderSlots.get(slot));
                    if (ladder == null) return;

                    if (hologram instanceof LadderStaticHologram staticHologram) {

                        staticHologram.setLadder(ladder);
                    } else if (hologram instanceof LadderDynamicHologram dynamicHologram) {

                        if (dynamicHologram.getLadders().contains(ladder))
                            dynamicHologram.getLadders().remove(ladder);
                        else
                            dynamicHologram.getLadders().add(ladder);
                    }

                    this.update();
                } else
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.HOLOGRAM.CANT-EDIT-ENABLED"));
            }
        }
    }

    private static ItemStack getLadderItem(Ladder ladder, boolean enabled) {
        if (enabled) {
            return GUIFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-LADDERS.ICONS.ENABLED-LADDER")
                    .replaceAll("%ladder%", ladder.getName())
                    .replaceAll("%ladderDisplayName%", ladder.getDisplayName())
                    .get();
        } else {
            return GUIFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-LADDERS.ICONS.DISABLED-LADDER")
                    .replaceAll("%ladder%", ladder.getName())
                    .replaceAll("%ladderDisplayName%", ladder.getDisplayName())
                    .get();
        }
    }

}
