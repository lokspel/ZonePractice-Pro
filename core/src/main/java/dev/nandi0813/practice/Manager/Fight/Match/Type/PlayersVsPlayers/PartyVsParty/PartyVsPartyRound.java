package dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PartyVsParty;

import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PlayersVsPlayersRound;
import dev.nandi0813.practice.Manager.Fight.Match.Util.EndMessageUtil;
import dev.nandi0813.practice.Manager.Fight.Match.Util.TeamUtil;

public class PartyVsPartyRound extends PlayersVsPlayersRound {

    protected PartyVsPartyRound(Match match, int roundNumber) {
        super(match, roundNumber);
    }

    @Override
    public void sendEndMessage(boolean endMatch) {
        PartyVsParty partyVsParty = (PartyVsParty) match;
        if (endMatch) {
            TeamEnum matchWinner = this.getMatch().getMatchWinner();
            if (matchWinner != null) {
                for (String message : EndMessageUtil.getEndMessage(partyVsParty, matchWinner, partyVsParty.getTeamPlayers(matchWinner), partyVsParty.getTeamPlayers(TeamUtil.getOppositeTeam(matchWinner))))
                    this.match.sendMessage(message, true);
            } else {
                for (String line : LanguageManager.getList("MATCH.PARTY-VS-PARTY.MATCH-END-DRAW"))
                    this.match.sendMessage(line, true);
            }
        } else {
            if (roundWinner != null) {
                for (String line : LanguageManager.getList("MATCH.PARTY-VS-PARTY.MATCH-END-ROUND"))
                    this.match.sendMessage(line
                            .replaceAll("%team%", roundWinner.getNameMM())
                            .replaceAll("%round%", String.valueOf((match.getWinsNeeded() - partyVsParty.getWonRounds(roundWinner)))), true);
            } else {
                for (String line : LanguageManager.getList("MATCH.PARTY-VS-PARTY.match-end-round-draw"))
                    this.match.sendMessage(line, true);
            }
        }
    }

}
