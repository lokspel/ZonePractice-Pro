package dev.nandi0813.practice.Manager.Ladder.Type;

import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.LadderHandle;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.Enum.LadderType;
import org.bukkit.event.Event;

public class Basic extends NormalLadder implements LadderHandle {

    public Basic(String name, LadderType type) {
        super(name, type);
    }

    @Override
    public boolean handleEvents(Event e, Match match) {
        return false;
    }

}
