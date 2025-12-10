package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items.Knockback;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Util.Forks.CarbonUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.refinedev.spigot.api.knockback.KnockbackAPI;
import xyz.refinedev.spigot.knockback.KnockbackProfile;

import java.util.ArrayList;
import java.util.List;

public class CarbonKnockbackItem extends KnockbackItem {

    public CarbonKnockbackItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, ladder);
    }

    @Override
    public void updateItemStack() {
        List<String> extension = new ArrayList<>();
        for (KnockbackProfile kt : KnockbackAPI.getInstance().getProfiles()) {
            String ktName = StringUtils.capitalize(kt.getName().toLowerCase());

            if (ladder.getLadderKnockback().getCarbonKnockbackProfile().equals(kt)) extension.add(" &a» " + ktName);
            else extension.add(" &7» " + ktName);
        }

        GUIItem guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.KNOCKBACK");

        List<String> lore = new ArrayList<>();
        for (String line : guiItem.getLore()) {
            if (line.contains("%knockbackTypes%"))
                lore.addAll(extension);
            else
                lore.add(line);
        }

        guiItem.setLore(lore);
        this.guiItem = guiItem;
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        ladder.getLadderKnockback().setCarbonKnockbackProfile(CarbonUtil.getNextKnockbackProfile(ladder.getLadderKnockback().getCarbonKnockbackProfile()));

        build(true);
    }

}
