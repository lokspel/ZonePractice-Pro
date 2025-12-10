package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.Arena.Util.ArenaWorldUtil;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public enum ArenasArg {
    ;

    public static void run(Player player) {
        if (!player.hasPermission("zpp.practice.arenas")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PRACTICE.NO-PERMISSION"));
            return;
        }

        ClassImport.getClasses().getPlayerUtil().clearInventory(player);
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);

        player.teleport(ArenaWorldUtil.getArenasWorld().getSpawnLocation());
    }

}
