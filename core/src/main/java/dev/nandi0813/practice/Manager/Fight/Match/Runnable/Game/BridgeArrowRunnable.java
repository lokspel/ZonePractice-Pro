package dev.nandi0813.practice.Manager.Fight.Match.Runnable.Game;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Util.Runnable.GameRunnable;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BridgeArrowRunnable extends GameRunnable {

    private static final int TIME = ConfigManager.getInt("MATCH-SETTINGS.LADDER-SETTINGS.BRIDGE.REGENERATING-ARROW.TIME");

    private final GUIItem guiItem = new GUIItem(Material.ARROW);

    public BridgeArrowRunnable(Player player, Match match) {
        super(player, match.getMatchPlayers().get(player), TIME, CooldownObject.BRIDGE_ARROW, ConfigManager.getBoolean("MATCH-SETTINGS.LADDER-SETTINGS.BRIDGE.REGENERATING-ARROW.EXP-BAR"));
    }

    @Override
    public void abstractCancel() {
        if (player.getInventory().firstEmpty() == -1) return;

        player.getInventory().addItem(guiItem.get());
        player.updateInventory();
    }

}
