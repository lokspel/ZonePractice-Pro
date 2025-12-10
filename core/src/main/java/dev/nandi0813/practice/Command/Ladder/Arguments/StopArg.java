package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public enum StopArg {
    ;

    public static void run(Player player, String label, String[] args) {
        if (!player.hasPermission("zpp.ladder.stop")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.NO-PERMISSION"));
            return;
        }

        if (args.length != 2) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.COMMAND-HELP").replace("%label%", label));
            return;
        }

        Ladder ladder = LadderManager.getInstance().getLadder(args[1]);
        if (ladder == null) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.NOT-EXISTS").replace("%ladder%", args[1]));
            return;
        }

        if (!ladder.isEnabled()) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.LADDER-DISABLED").replace("%ladder%", ladder.getDisplayName()));
            return;
        }

        List<Match> matches = MatchManager.getInstance().getLiveMatchesByLadder(ladder);
        if (matches.isEmpty()) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.NO-MATCHES").replace("%ladder%", ladder.getDisplayName()));
            return;
        }

        for (Match match : matches) {
            match.sendMessage(LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.MATCH-END-MESSAGE").replace("%player%", player.getName()), true);
            match.endMatch();
        }
        Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.PLAYER-ENDED").replace("%ladder%", ladder.getDisplayName()));
    }

    public static List<String> tabComplete(Player player, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (!player.hasPermission("zpp.setup")) return arguments;

        if (args.length == 2) {
            for (Ladder ladder : LadderManager.getInstance().getLadders()) {
                if (!ladder.isEnabled()) continue;

                arguments.add(ladder.getName());
            }

            return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
        }

        return arguments;
    }

}
