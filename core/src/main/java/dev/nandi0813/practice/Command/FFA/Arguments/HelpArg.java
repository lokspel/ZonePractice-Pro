package dev.nandi0813.practice.Command.FFA.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum HelpArg {
    ;

    public static void run(Player player, String label) {
        for (String line : LanguageManager.getList("FFA.COMMAND.HELP")) {
            Common.sendMMMessage(player, line.replace("%label%", label));
        }
    }

}
