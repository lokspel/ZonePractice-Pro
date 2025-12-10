package dev.nandi0813.practice.Manager.Fight.Util.Runnable;

import dev.nandi0813.practice.Manager.Fight.Util.FightPlayer;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import org.bukkit.entity.Player;

public class EnderPearlRunnable extends GameRunnable {

    public EnderPearlRunnable(Player player, FightPlayer fightPlayer, int seconds, boolean expBar) {
        super(player, fightPlayer, seconds, CooldownObject.ENDER_PEARL, expBar);
    }

    @Override
    public void abstractCancel() {
    }

}
