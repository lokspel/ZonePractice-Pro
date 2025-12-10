package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum PartyDisbandArg {
    ;

    public static void DisbandCommand(Player player, String label, String[] args) {
        if (args.length != 1) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.COMMAND-HELP").replaceAll("%label%", label));
            return;
        }

        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.NO-PARTY"));
            return;
        }

        if (!party.getLeader().equals(player)) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.NOT-LEADER"));
            return;
        }

        party.disband();
    }

}
