package dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.Items.Knockback;

import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSettings.Settings.SettingsGui;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Util.Forks.FoxSpigotUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import pt.foxspigot.jar.knockback.KnockbackModule;
import pt.foxspigot.jar.knockback.KnockbackProfile;

import java.util.ArrayList;
import java.util.List;

public class FoxSpigotKnockbackItem extends KnockbackItem {

    public FoxSpigotKnockbackItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, ladder);
    }

    @Override
    public void updateItemStack() {
        List<String> extension = new ArrayList<>();
        for (KnockbackProfile kt : KnockbackModule.INSTANCE.profiles.values()) {
            String ktName = StringUtils.capitalize(kt.title.toLowerCase());

            if (ladder.getLadderKnockback().getFoxspigotKnockbackProfile().equals(kt)) extension.add(" &a» " + ktName);
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
        ladder.getLadderKnockback().setFoxspigotKnockbackProfile(FoxSpigotUtil.getNextKnockbackProfile(ladder.getLadderKnockback().getFoxspigotKnockbackProfile()));

        build(true);
    }

}
