package dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers;

import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Round;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class PlayersVsPlayersRound extends Round {

    protected TeamEnum roundWinner;

    protected PlayersVsPlayersRound(Match match, int roundNumber) {
        super(match, roundNumber);
    }

    @Override
    public PlayersVsPlayers getMatch() {
        return (PlayersVsPlayers) this.match;
    }

}
