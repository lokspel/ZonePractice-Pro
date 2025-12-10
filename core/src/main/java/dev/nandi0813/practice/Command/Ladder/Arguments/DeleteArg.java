package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.Setup.Arena.ArenaSetupManager;
import dev.nandi0813.practice.Manager.GUI.Setup.Ladder.LadderSetupManager;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Util.Common;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public enum DeleteArg {
    ;

    public static void run(Player player, String label, String[] args) {
        if (!player.hasPermission("zpp.setup")) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.NO-PERMISSION"));
            return;
        }

        if (args.length != 2) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.DELETE.COMMAND-HELP").replace("%label%", label));
            return;
        }

        NormalLadder ladder = LadderManager.getInstance().getLadder(args[1]);
        if (ladder == null) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.DELETE.NOT-EXISTS").replace("%ladder%", args[1]));
            return;
        }

        if (ladder.isEnabled()) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.DELETE.LADDER-ENABLED").replace("%ladder%", ladder.getDisplayName()));
            return;
        }

        // Delete the ladder data.
        ladder.deleteData();
        // Delete the ladder from the ladders list.
        LadderManager.getInstance().getLadders().remove(ladder);
        LadderManager.getInstance().getLadders().sort(Comparator.comparing(Ladder::getName));

        // Remove the ladder from the arenas and update the ladder GUI in the arena settings.
        ArenaManager.getInstance().removeLadder(ladder);
        for (Map<GUIType, GUI> map : ArenaSetupManager.getInstance().getArenaSetupGUIs().values())
            map.get(GUIType.Arena_Ladders_Single).update();

        GUIManager.getInstance().searchGUI(GUIType.Queue_Unranked).update();
        if (ladder.isRanked())
            GUIManager.getInstance().searchGUI(GUIType.Queue_Ranked).update();

        // Update GUIs.
        LadderSetupManager.getInstance().removeLadderGUIs(ladder);

        Common.sendMMMessage(player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.DELETE.DELETE-SUCCESS").replace("%ladder%", ladder.getDisplayName()));
    }

    public static List<String> tabComplete(Player player, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (!player.hasPermission("zpp.setup")) return arguments;

        if (args.length == 2) {
            for (Ladder ladder : LadderManager.getInstance().getLadders()) {
                if (!ladder.isEnabled())
                    arguments.add(ladder.getName());
            }

            return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
        }

        return arguments;
    }

}
