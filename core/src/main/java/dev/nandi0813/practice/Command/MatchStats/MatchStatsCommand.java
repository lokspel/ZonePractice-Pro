package dev.nandi0813.practice.Command.MatchStats;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MatchStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            Common.sendConsoleMMMessage(LanguageManager.getString("CANT-USE-CONSOLE"));
            return false;
        }

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
            return false;

        if (args.length != 3) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.MATCH-STAT.COMMAND-HELP"));
            return false;
        }

        Match match = MatchManager.getInstance().getMatches().get(args[0]);
        if (match == null) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.MATCH-STAT.INVALID-MATCH-ID"));
            return false;
        }

        UUID target = UUID.fromString(args[1]);
        if (!match.getMatchStatsGuis().containsKey(target)) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.MATCH-STAT.INVALID-PLAYER"));
            return false;
        }

        int round = Integer.parseInt(args[2]);
        if (!match.getMatchStatsGuis().get(target).getGui().containsKey(round)) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.MATCH-STAT.INVALID-ROUND"));
            return false;
        }

        match.getMatchStatsGuis().get(target).open(player, round);

        return true;
    }

}
