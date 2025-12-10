package dev.nandi0813.practice.Command.Event.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum HelpArg {
    ;

    public static void run(Player player, String label) {
        if (player.hasPermission("zpp.setup") && player.hasPermission("zpp.event.stop")) {
            for (String line : LanguageManager.getList("COMMAND.EVENT.ARGUMENTS.HELP.STAFF"))
                Common.sendMMMessage(player, line.replace("%label%", label));
        } else if (player.hasPermission("zpp.event.join") || player.hasPermission("zpp.event.host")) {
            for (String line : LanguageManager.getList("COMMAND.EVENT.ARGUMENTS.HELP.USER"))
                Common.sendMMMessage(player, line.replace("%label%", label));
        } else
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.EVENT.NO-PERMISSION"));
    }

}
