package dev.nandi0813.practice.Command.FFA.Arguments;

import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.Arena.Arenas.FFAArena;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public enum SpectateArg {
    ;

    public static void run(Player player, String label, String[] args) {
        if (args.length != 2) {
            Common.sendMMMessage(player, LanguageManager.getString("FFA.COMMAND.SPECTATE.HELP").replace("%label%", label));
            return;
        }

        FFAArena ffaArena = ArenaManager.getInstance().getFFAArena(args[1]);
        if (ffaArena == null) {
            Common.sendMMMessage(player, LanguageManager.getString("FFA.COMMAND.SPECTATE.ARENA-NOT-FOUND"));
            return;
        }

        FFA ffa = ffaArena.getFfa();
        if (ffa == null || !ffa.isOpen()) {
            Common.sendMMMessage(player, LanguageManager.getString("FFA.COMMAND.SPECTATE.ARENA-CLOSED").replaceAll("%arena%", ffaArena.getDisplayName()));
            return;
        }

        Profile profile = ProfileManager.getInstance().getProfile(player);
        if (!profile.getStatus().equals(ProfileStatus.LOBBY)) {
            Common.sendMMMessage(player, LanguageManager.getString("FFA.COMMAND.SPECTATE.CANT-JOIN-FFA"));
            return;
        }

        ffa.addSpectator(player, null, true, true);
    }

    public static List<String> tabComplete(Player player, String[] args) {
        Profile profile = ProfileManager.getInstance().getProfile(player);
        List<String> arguments = new ArrayList<>();

        if (!profile.getStatus().equals(ProfileStatus.LOBBY))
            return arguments;

        if (args.length == 2) {
            for (FFAArena ffaArena : ArenaManager.getInstance().getFFAArenas()) {
                FFA ffa = ffaArena.getFfa();
                if (ffa != null && ffa.isOpen()) {
                    arguments.add(ffaArena.getName());
                }
            }

            return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
        }

        return arguments;
    }

}
