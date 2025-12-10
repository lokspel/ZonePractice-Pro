package dev.nandi0813.practice.Command.SingleCommands;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Division.Division;
import dev.nandi0813.practice.Manager.Division.DivisionManager;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Manager.Queue.QueueManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (!profile.getStatus().equals(ProfileStatus.LOBBY) || profile.isStaffMode() || profile.isParty()) {
            Common.sendMMMessage(player, LanguageManager.getString("CANT-USE-COMMAND"));
            return false;
        }

        if (!player.hasPermission("zpp.bypass.ranked.requirements")) {
            Division requirement = DivisionManager.getInstance().getMinimumForRanked();
            if (requirement != null && (requirement.getExperience() > profile.getStats().getExperience() || requirement.getWin() > profile.getStats().getGlobalWins())) {
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.QUEUES.RANKED.DIVISION-REQUIREMENT")
                        .replaceAll("%division_fullName%", requirement.getFullName())
                        .replaceAll("%division_shortName%", requirement.getShortName())
                );

                return false;
            }
        }

        if (profile.getRankedLeft() <= 0 && !player.hasPermission("zpp.bypass.ranked.limit")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.QUEUES.RANKED.NO-RANKED-LEFT"));
            return false;
        }

        if (args.length == 0) {
            GUIManager.getInstance().searchGUI(GUIType.Queue_Ranked).open(player);
        } else if (args.length == 1) {
            NormalLadder ladder = LadderManager.getInstance().getLadder(args[0]);
            if (ladder == null) {
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.QUEUES.RANKED.LADDER-NOT-FOUND").replaceAll("%ladder%", args[0]));
                return false;
            }

            QueueManager.getInstance().createRankedQueue(player, ladder);
        }

        return true;
    }

}
