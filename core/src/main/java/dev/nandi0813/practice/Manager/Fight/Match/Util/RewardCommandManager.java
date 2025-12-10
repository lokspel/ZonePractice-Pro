package dev.nandi0813.practice.Manager.Fight.Match.Util;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Fight.Match.Type.Duel.Duel;
import dev.nandi0813.practice.Manager.Server.ServerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardCommandManager {

    private static RewardCommandManager instance;

    public static RewardCommandManager getInstance() {
        if (instance == null)
            instance = new RewardCommandManager();
        return instance;
    }

    private final Map<Boolean, List<String>> winnerCommand = new HashMap<>();
    private final Map<Boolean, List<String>> loserCommand = new HashMap<>();

    private RewardCommandManager() {
        this.winnerCommand.put(false, ConfigManager.getList("MATCH-SETTINGS.END-COMMAND.DUEL.UNRANKED.WINNER-COMMANDS"));
        this.loserCommand.put(false, ConfigManager.getList("MATCH-SETTINGS.END-COMMAND.DUEL.UNRANKED.LOSER-COMMANDS"));

        this.winnerCommand.put(true, ConfigManager.getList("MATCH-SETTINGS.END-COMMAND.DUEL.RANKED.WINNER-COMMANDS"));
        this.loserCommand.put(true, ConfigManager.getList("MATCH-SETTINGS.END-COMMAND.DUEL.RANKED.LOSER-COMMANDS"));
    }

    public void executeCommands(Duel duel, boolean ranked) {
        if (duel.getMatchWinner() != null && this.winnerCommand.containsKey(ranked)) {
            for (String command : this.winnerCommand.get(ranked)) {
                if (!command.isEmpty()) {
                    ServerManager.runConsoleCommand(command.replace("%player%", duel.getMatchWinner().getName()));
                }
            }
        }

        if (duel.getLoser() != null && this.loserCommand.containsKey(ranked)) {
            for (String command : this.loserCommand.get(ranked)) {
                if (!command.isEmpty()) {
                    ServerManager.runConsoleCommand(command.replace("%player%", duel.getLoser().getName()));
                }
            }
        }
    }

}
