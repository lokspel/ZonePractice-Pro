package dev.nandi0813.practice.Manager.GUI.Setup.Server;

import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Backend.MysqlManager;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Leaderboard.Hologram.HologramManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ServerSaveGui extends GUI {

    private final GUI backTo;

    public ServerSaveGui(GUI backTo) {
        super(GUIType.Server_Save);
        this.gui.put(1, InventoryUtil.createInventory(GUIFile.getString("GUIS.SETUP.SERVER.FILE-SAVE.TITLE"), 1));
        this.backTo = backTo;
    }

    @Override
    public void build() {
        update();
    }

    @Override
    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
        {
            Inventory inventory = gui.get(1);

            for (int i : new int[]{1, 2})
                inventory.setItem(i, GUIManager.getFILLER_ITEM());
            inventory.setItem(0, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.BACK-TO").get());

            inventory.setItem(3, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Arena").get());
            inventory.setItem(4, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Ladder").get());
            inventory.setItem(5, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Event").get());
            inventory.setItem(6, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Player").get());
            inventory.setItem(7, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Hologram").get());
            inventory.setItem(8, GUIFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE").replaceAll("%data%", "Mysql").get());

            updatePlayers();
        });
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        Inventory inventory = e.getClickedInventory();

        e.setCancelled(true);

        if (inventory == null) return;

        if (inventory.getSize() > slot) {
            switch (slot) {
                case 0:
                    backTo.update();
                    backTo.open(player);
                    break;
                case 3:
                    Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                            ArenaManager.getInstance().saveArenas());
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    break;
                case 4:
                    Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                            LadderManager.getInstance().saveLadders());
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    break;
                case 5:
                    Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                            EventManager.getInstance().saveEventData());
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    break;
                case 6:
                    Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                            ProfileManager.getInstance().saveProfiles());
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    break;
                case 7:
                    Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                            HologramManager.getInstance().saveHolograms());
                    Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    break;
                case 8:
                    if (MysqlManager.isConnected(false)) {
                        Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
                                ServerManager.getInstance().getMysqlSaveRunnable().save());
                        Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
                    } else
                        Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.SERVER.MYSQL-DISABLED"));
                    break;
            }
        }
    }

}
