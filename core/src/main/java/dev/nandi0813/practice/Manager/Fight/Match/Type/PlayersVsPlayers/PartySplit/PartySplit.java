package dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PartySplit;

import dev.nandi0813.practice.Manager.Arena.Arenas.Arena;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PlayersVsPlayers;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.PlayerDisplay.Nametag.NametagManager;
import dev.nandi0813.practice.Util.PlayerUtil.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class PartySplit extends PlayersVsPlayers {

    public PartySplit(Ladder ladder, Arena arena, Party party, int winsNeeded) {
        super(ladder, arena, new ArrayList<>(party.getMembers()), winsNeeded);

        this.type = MatchType.PARTY_SPLIT;

        /*
         * Split the players into teams
         */
        Collections.shuffle(this.players);
        int team1PlayerCount = 0;
        int team2PlayerCount = 0;
        for (Player player : players) {
            if (team2PlayerCount > team1PlayerCount) {
                this.teams.get(TeamEnum.TEAM1).add(player);
                NametagManager.getInstance().setNametag(player, TeamEnum.TEAM1.getPrefix(), TeamEnum.TEAM1.getNameColor(), TeamEnum.TEAM1.getSuffix(), 20);

                team1PlayerCount++;
            } else {
                this.teams.get(TeamEnum.TEAM2).add(player);
                NametagManager.getInstance().setNametag(player, TeamEnum.TEAM2.getPrefix(), TeamEnum.TEAM2.getNameColor(), TeamEnum.TEAM2.getSuffix(), 21);

                team2PlayerCount++;
            }
        }
    }

    @Override
    public void startNextRound() {
        PartySplitRound round = new PartySplitRound(this, this.rounds.size() + 1);
        this.rounds.put(round.getRoundNumber(), round);

        if (round.getRoundNumber() == 1) {
            for (String line : LanguageManager.getList("MATCH.PARTY-SPLIT.MATCH-START")) {
                this.sendMessage(line
                        .replaceAll("%matchTypeName%", MatchType.PARTY_SPLIT.getName(false))
                        .replaceAll("%ladder%", ladder.getDisplayName())
                        .replaceAll("%map%", arena.getDisplayName())
                        .replaceAll("%rounds%", String.valueOf(this.winsNeeded))
                        .replaceAll("%team1name%", TeamEnum.TEAM1.getNameMM())
                        .replaceAll("%team2name%", TeamEnum.TEAM2.getNameMM())
                        .replaceAll("%team1players%", PlayerUtil.getPlayerNames(teams.get(TeamEnum.TEAM1)).toString().replace("[", "").replace("]", ""))
                        .replaceAll("%team2players%", PlayerUtil.getPlayerNames(teams.get(TeamEnum.TEAM2)).toString().replace("[", "").replace("]", "")), false);
            }
        }

        round.startRound();
    }

}
