package dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface;

import dev.nandi0813.practice.Manager.Fight.Match.Match;
import org.bukkit.event.Event;

public interface LadderHandle {

    boolean handleEvents(Event e, Match match);

}
