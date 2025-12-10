package dev.nandi0813.practice.Manager.Fight.Match.Interface;

import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import org.bukkit.entity.Player;

public interface Team {

    TeamEnum getTeam(Player player);

}
