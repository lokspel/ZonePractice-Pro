package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingType;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StartCountdownItem extends SettingItem {

    public StartCountdownItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, SettingType.START_COUNTDOWN, ladder);
    }

    @Override
    public void updateItemStack() {
        guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.START-COUNTDOWN")
                .replaceAll("%startCountdown%", String.valueOf(ladder.getStartCountdown()));
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        int startCountdown = ladder.getStartCountdown();

        if (e.getClick().isLeftClick() && startCountdown > 2)
            ladder.setStartCountdown(startCountdown - 1);
        else if (e.getClick().isRightClick() && startCountdown < 5)
            ladder.setStartCountdown(startCountdown + 1);

        build(true);
    }

}
