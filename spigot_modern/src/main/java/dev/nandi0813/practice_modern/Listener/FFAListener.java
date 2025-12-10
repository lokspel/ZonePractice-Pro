package dev.nandi0813.practice_modern.Listener;

import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Fight.FFA.FFAManager;
import dev.nandi0813.practice.Manager.Fight.Util.DeathCause;
import dev.nandi0813.practice.Manager.Fight.Util.FightUtil;
import dev.nandi0813.practice.Manager.Fight.Util.Stats.Statistic;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FFAListener extends dev.nandi0813.practice.Manager.Fight.FFA.FFAListener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        Profile profile = ProfileManager.getInstance().getProfile(player);
        if (profile == null) return;

        FFA ffa = FFAManager.getInstance().getFFAByPlayer(player);
        if (ffa == null) return;

        e.setCancelled(true);

        DamageSource damageSource = e.getDamageSource();
        Player killer = null;
        if (damageSource.getCausingEntity() instanceof Entity damageEntity) {
            killer = FightUtil.getKiller(damageEntity);
        }

        DeathCause cause = dev.nandi0813.practice_modern.Listener.FightUtil.convert(damageSource.getDamageType());
        ffa.killPlayer(player, killer, cause.getMessage().replaceAll("%killer%", killer != null ? killer.getName() : "Unknown"));

        if (killer != null) {
            Statistic statistic = ffa.getStatistics().get(killer);
            statistic.setKills(statistic.getKills() + 1);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        Profile profile = ProfileManager.getInstance().getProfile(player);
        if (profile == null) return;

        FFA ffa = FFAManager.getInstance().getFFAByPlayer(player);
        if (ffa == null) return;

        if (!(e.getEntity() instanceof Player target)) return;

        if (e.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player attacker) {

                if (projectile instanceof Arrow) {
                    arrowDisplayHearth(attacker, target, e.getFinalDamage());
                }
            }
        }
    }

}
