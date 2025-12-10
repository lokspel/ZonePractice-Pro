package dev.nandi0813.practice.Command.FFA.Arguments;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Fight.FFA.FFAManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;

public enum LeaveArg {
    ;

    public static void run(Player player) {
        FFA ffa = FFAManager.getInstance().getFFAByPlayer(player);

        if (ffa == null) {
            Common.sendMMMessage(player, LanguageManager.getString("FFA.COMMAND.LEAVE.NOT-IN-FFA"));
            return;
        }

        ffa.removePlayer(player);
    }

}
