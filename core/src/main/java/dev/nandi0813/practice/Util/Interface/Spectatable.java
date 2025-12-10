package dev.nandi0813.practice.Util.Interface;

import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Util.Cuboid;
import dev.nandi0813.practice.Util.FightMapChange.FightChange;
import org.bukkit.entity.Player;

import java.util.List;

public interface Spectatable {

    List<Player> getSpectators();

    void addSpectator(Player spectator, Player target, boolean teleport, boolean message);

    void removeSpectator(Player player);

    boolean canDisplay();

    GUIItem getSpectatorMenuItem();

    Cuboid getCuboid();

    void sendMessage(String message, boolean spectate);

    FightChange getFightChange();

}
