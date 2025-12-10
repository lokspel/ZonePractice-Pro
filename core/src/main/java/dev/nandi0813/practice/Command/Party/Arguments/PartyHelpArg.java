package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum PartyHelpArg {
    ;

    public static void HelpCommand(Player player, String label) {
        for (String line : LanguageManager.getList("COMMAND.PARTY.ARGUMENTS.HELP"))
            Common.sendMMMessage(player, line.replaceAll("%label%", label));
    }

}
