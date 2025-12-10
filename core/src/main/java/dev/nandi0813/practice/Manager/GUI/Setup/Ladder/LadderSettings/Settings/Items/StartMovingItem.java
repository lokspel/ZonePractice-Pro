package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StartMovingItem extends SettingItem {

    public StartMovingItem(SettingsGui settingsGui, final NormalLadder ladder) {
        super(settingsGui, SettingType.START_MOVING, ladder);
    }

    @Override
    public void updateItemStack() {
        if (ladder.isStartMove())
            this.guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.START-MOVING.ENABLED").setGlowing(true);
        else
            this.guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.START-MOVING.DISABLED");
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        ladder.setStartMove(!ladder.isStartMove());

        this.build(true);
    }
}
