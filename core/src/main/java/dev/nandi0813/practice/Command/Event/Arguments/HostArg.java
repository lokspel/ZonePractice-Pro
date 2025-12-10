package dev.nandi0813.practice.Command.Event.Arguments;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventStatus;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum HostArg {
    ;

    public static void run(Player player, String label, String[] args) {
        if (args.length != 1) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.COMMAND-HELP").replace("%label%", label));
            return;
        }

        if (!player.hasPermission("zpp.event.host")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.NO-PERMISSION"));
            return;
        }

        if (!EventManager.getInstance().getEvents().isEmpty() && ConfigManager.getBoolean("EVENT.MULTIPLE")) {
            for (Event event : EventManager.getInstance().getEvents()) {
                if (!event.getStatus().equals(EventStatus.COLLECTING)) continue;

                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.CANT-HOST-NOW"));
                return;
            }
        } else if (!EventManager.getInstance().getEvents().isEmpty() && !ConfigManager.getBoolean("EVENT.MULTIPLE")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.CANT-HOST-MULTIPLE"));
            return;
        }

        GUIManager.getInstance().searchGUI(GUIType.Event_Host).open(player);
    }

}
