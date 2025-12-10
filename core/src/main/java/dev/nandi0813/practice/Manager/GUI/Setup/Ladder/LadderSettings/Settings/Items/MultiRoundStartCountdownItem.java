package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MultiRoundStartCountdownItem extends SettingItem {

    public MultiRoundStartCountdownItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, SettingType.MULTI_ROUND_START_COUNTDOWN, ladder);
    }

    @Override
    public void updateItemStack() {
        if (ladder.isMultiRoundStartCountdown())
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.MULTI-ROUND-START-COUNTDOWN.ENABLED");
        else
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.MULTI-ROUND-START-COUNTDOWN.DISABLED");
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        ladder.setMultiRoundStartCountdown(!ladder.isMultiRoundStartCountdown());

        build(true);
    }

}
