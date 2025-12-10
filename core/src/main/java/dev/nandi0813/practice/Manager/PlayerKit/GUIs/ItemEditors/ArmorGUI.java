package dev.nandi0813.practice.Manager.PlayerKit.GUIs.ItemEditors;

import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.PlayerKit.Items.EditorIcon;
import dev.nandi0813.practice.Manager.PlayerKit.Items.KitItem;
import dev.nandi0813.practice.Manager.PlayerKit.PlayerKitEditing;
import dev.nandi0813.practice.Manager.PlayerKit.PlayerKitManager;
import dev.nandi0813.practice.Manager.PlayerKit.StaticItems;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorGUI extends ItemEditor {

    public ArmorGUI(GUIType type, List<EditorIcon> armorIcons) {
        super(type, new ArrayList<>(Arrays.asList(
                StaticItems.MAIN_ARMOR_BACK_TO_ICON,
                StaticItems.MAIN_ARMOR_NONE_ICON
        )));

        this.gui.put(1, InventoryUtil.createInventory(StaticItems.MAIN_ARMOR_GUI_TITLE, StaticItems.MAIN_ARMOR_GUI_SIZE));

        this.icons.addAll(armorIcons);
        this.build();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        PlayerKitEditing playerKitEditing = PlayerKitManager.getInstance().getEditing().get(player);
        int slot = e.getRawSlot();
        EditorIcon icon = getIcon(slot);

        if (icon == null) {
            return;
        }

        KitItem kitItem = playerKitEditing.getKitItem();
        if (kitItem != null) {
            if (icon.equals(StaticItems.MAIN_ARMOR_NONE_ICON)) {
                kitItem.reset();
            } else if (!icon.equals(StaticItems.MAIN_ARMOR_BACK_TO_ICON)) {
                kitItem.setItemStack(icon.getForPlayerKit());
            }
        }

        GUI mainGUI = playerKitEditing.getCustomLadder().getMainGUI();
        mainGUI.update();
        mainGUI.open(player);
    }

    @Override
    public void handleCloseEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        PlayerKitEditing playerKitEditing = PlayerKitManager.getInstance().getEditing().get(player);
        GUI mainGUI = playerKitEditing.getCustomLadder().getMainGUI();

        Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () -> {
            if (GUIManager.getInstance().getOpenGUI().containsKey(player)) {
                return;
            }

            mainGUI.open(player);
        }, 5L);
    }

}
