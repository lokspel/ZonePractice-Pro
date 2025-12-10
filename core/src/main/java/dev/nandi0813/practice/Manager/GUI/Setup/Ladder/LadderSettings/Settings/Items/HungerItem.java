package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HungerItem extends SettingItem {

    public HungerItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, SettingType.HUNGER, ladder);
    }

    @Override
    public void updateItemStack() {
        if (ladder.isHunger())
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.HUNGER.ENABLED").setGlowing(true);
        else
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.HUNGER.DISABLED");
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        ladder.setHunger(!ladder.isHunger());

        build(true);
    }

}
