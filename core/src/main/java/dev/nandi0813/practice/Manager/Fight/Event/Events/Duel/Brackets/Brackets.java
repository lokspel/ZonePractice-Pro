package dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Brackets;

import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Interface.DuelEvent;
import dev.nandi0813.practice.Util.PlayerUtil.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Brackets extends DuelEvent {

    public Brackets(Object starter, BracketsData eventData) {
        super(starter, eventData, "COMMAND.EVENT.ARGUMENTS.BRACKETS");
    }

    @Override
    public BracketsData getEventData() {
        return (BracketsData) eventData;
    }

    @Override
    public void teleport(Player player, Location location) {
        player.teleport(location);
        PlayerUtil.setFightPlayer(player);

        this.getEventData().getKitData().loadKitData(player, true);
    }

}
