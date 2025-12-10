package dev.nandi0813.practice.Manager.GUI.GUIs.Selectors;

import dev.nandi0813.practice.Manager.Arena.Arenas.Arena;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Type.Duel.Duel;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PartyFFA.PartyFFA;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PartySplit.PartySplit;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Party.Party;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class MatchStarterGui extends GUI {

    protected final MatchType matchType;
    protected final Ladder ladder;
    protected final GUI backTo;

    public MatchStarterGui(GUIType type, MatchType matchType, Ladder ladder, GUI backTo) {
        super(type);

        this.matchType = matchType;
        this.ladder = ladder;
        this.backTo = backTo;
    }

    @Nullable
    protected Match getMatch(Party party, Arena arena, int rounds) {
        Match match = null;
        List<Player> matchPlayers = new ArrayList<>(party.getMembers());

        if (party.getMembers().size() == 2) {
            match = new Duel(ladder, arena, matchPlayers, false, rounds);
        } else {
            if (matchType.equals(MatchType.PARTY_FFA)) {
                match = new PartyFFA(ladder, arena, party, rounds);
            } else if (matchType.equals(MatchType.PARTY_SPLIT)) {
                match = new PartySplit(ladder, arena, party, rounds);
            }
        }
        return match;
    }

}
