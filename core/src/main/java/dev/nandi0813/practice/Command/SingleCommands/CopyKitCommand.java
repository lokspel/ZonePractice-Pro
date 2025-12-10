package dev.nandi0813.practice.Command.SingleCommands;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.PlayerKit.PlayerKitManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CopyKitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("zpp.playerkit.copy")) {
            Common.sendMMMessage(player, LanguageManager.getString("CUSTOM-PLAYER-KIT.NO-PERMISSION"));
            return false;
        }

        Profile profile = ProfileManager.getInstance().getProfile(player);
        if (profile.getGroup() == null || profile.getGroup().getCustomKitLimit() <= 0) {
            Common.sendMMMessage(player, LanguageManager.getString("CUSTOM-PLAYER-KIT.COPYKIT.NO-CUSTOM-KIT"));
            return false;
        }

        if (args.length != 1) {
            Common.sendMMMessage(player, LanguageManager.getString("CUSTOM-PLAYER-KIT.COPYKIT.HELP"));
            return false;
        }

        String code = args[0];
        if (!PlayerKitManager.getInstance().getCopy().containsKey(code)) {
            Common.sendMMMessage(player, LanguageManager.getString("CUSTOM-PLAYER-KIT.COPYKIT.NO-CODE"));
            return false;
        }

        PlayerKitManager.getInstance().getCopying().put(player, PlayerKitManager.getInstance().getCopy().get(code));
        profile.getPlayerCustomKitSelector().open(player);

        return true;
    }

}
