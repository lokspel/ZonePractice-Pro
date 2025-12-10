package dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PartySplit;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PlayersVsPlayersRound;
import dev.nandi0813.practice.Manager.Fight.Match.Util.EndMessageUtil;
import dev.nandi0813.practice.Manager.Fight.Match.Util.TeamUtil;

public class PartySplitRound extends PlayersVsPlayersRound {

    protected PartySplitRound(Match match, int roundNumber) {
        super(match, roundNumber);
    }

    @Override
    public void sendEndMessage(boolean endMatch) {
        PartySplit partySplit = (PartySplit) match;
        if (endMatch) {
            TeamEnum matchWinner = this.getMatch().getMatchWinner();
            if (matchWinner != null) {
                for (String message : EndMessageUtil.getEndMessage(partySplit, matchWinner, partySplit.getTeamPlayers(matchWinner), partySplit.getTeamPlayers(TeamUtil.getOppositeTeam(matchWinner))))
                    this.match.sendMessage(message, true);
            } else {
                for (String line : LanguageManager.getList("MATCH.PARTY-SPLIT.MATCH-END-DRAW"))
                    this.match.sendMessage(line, true);
            }
        } else {
            if (roundWinner != null) {
                for (String line : LanguageManager.getList("MATCH.PARTY-SPLIT.MATCH-END-ROUND"))
                    this.match.sendMessage(line
                            .replaceAll("%team%", roundWinner.getNameMM())
                            .replaceAll("%round%", String.valueOf((match.getWinsNeeded() - partySplit.getWonRounds(roundWinner)))), true);
            } else {
                for (String line : LanguageManager.getList("MATCH.PARTY-SPLIT.MATCH-END-ROUND-DRAW"))
                    this.match.sendMessage(line, true);
            }
        }
    }

}
