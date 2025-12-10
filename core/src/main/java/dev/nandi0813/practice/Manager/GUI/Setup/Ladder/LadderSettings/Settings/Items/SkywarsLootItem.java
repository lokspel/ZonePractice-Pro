package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSetupManager;
import dev.nandi0813.practice.Manager.Ladder.Type.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SkywarsLootItem extends SettingItem {

    private final SkyWars skyWars;

    public SkywarsLootItem(SettingsGui settingsGui, SkyWars skyWars) {
        super(settingsGui, SettingType.SKYWARS_LOOT, skyWars);
        this.skyWars = skyWars;
    }

    @Override
    public void updateItemStack() {
        this.guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.SKYWARS");
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        LadderSetupManager.getInstance().getLadderSetupGUIs().get(skyWars).get(GUIType.Ladder_SkyWarsLoot).open(((Player) e.getWhoClicked()));
    }

}
