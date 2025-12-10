package dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Sumo;

import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Interface.DuelEvent;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.PlayerUtil.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sumo extends DuelEvent {

    public Sumo(Object starter, SumoData sumoData) {
        super(starter, sumoData, "COMMAND.EVENT.ARGUMENTS.SUMO");
    }

    @Override
    public void teleport(Player player, Location location) {
        ClassImport.getClasses().getPlayerUtil().clearInventory(player);
        PlayerUtil.setFightPlayer(player);
        player.teleport(location);
    }

}
