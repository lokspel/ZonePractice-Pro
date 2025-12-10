package dev.nandi0813.practice_modern.Interfaces;

import dev.nandi0813.practice.Manager.Profile.Profile;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ActionBar extends dev.nandi0813.practice.Module.Interfaces.ActionBar.ActionBar {

    public ActionBar(Profile profile) {
        super(profile);
    }

    @Override
    public void send() {
        Player player = profile.getPlayer().getPlayer();
        if (player != null) {
            player.sendActionBar(this.message);
        }
    }

    @Override
    public void clear() {
        Player player = profile.getPlayer().getPlayer();
        if (player != null) {
            player.sendActionBar(Component.empty());
        }
    }

}
