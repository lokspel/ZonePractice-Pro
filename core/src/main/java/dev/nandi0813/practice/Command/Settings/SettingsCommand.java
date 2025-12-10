package dev.nandi0813.practice.Command.Settings;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            Common.sendConsoleMMMessage(LanguageManager.getString("CANT-USE-CONSOLE"));
            return false;
        }

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (!profile.getStatus().equals(ProfileStatus.LOBBY) && !profile.getStatus().equals(ProfileStatus.STAFF_MODE)) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETTINGS.CANT-USE"));
            return false;
        }

        if (!player.hasPermission("zpp.settings.open")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETTINGS.NO-PERMISSION"));
            return false;
        }

        profile.getSettingsGui().open(player);

        return true;
    }

}
