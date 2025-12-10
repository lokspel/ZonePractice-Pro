package dev.nandi0813.practice.Manager.Queue.Runnables;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Queue.Queue;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.scheduler.BukkitRunnable;

public class RankedSearchRunnable extends SearchRunnable {

    private static final int RANGE_INCREASE = ConfigManager.getConfig().getInt("QUEUE.RANKED.ELO-RANGE-INCREASE");
    private static final int ELO_RANGE_TIME = ConfigManager.getInt("QUEUE.RANKED.ELO-RANGE-TIME");
    private int range = ConfigManager.getConfig().getInt("QUEUE.RANKED.ELO-RANGE-INCREASE");

    private final int elo = queue.getProfile().getStats().getLadderStat(queue.getLadder()).getElo();

    public RankedSearchRunnable(final Queue queue) {
        super(queue, 0, 20L * ELO_RANGE_TIME, false);

        this.searching = new BukkitRunnable() {
            @Override
            public void run() {
                for (Queue q : queueManager.getQueues()) {
                    if (q == queueManager.getQueue(queue.getPlayer()))
                        continue;

                    if (q.getPlayer() == queue.getPlayer())
                        continue;

                    if (q.getLadder() != queue.getLadder())
                        continue;

                    if (!q.isRanked())
                        continue;

                    int queueElo = q.getProfile().getStats().getLadderStat(queue.getLadder()).getElo();
                    if ((elo - range) <= queueElo && (elo + range) >= queueElo) {
                        queue.startMatch(q);

                        this.cancel();
                        return;
                    }
                }
            }
        }.runTaskTimer(ZonePractice.getInstance(), 0, 20L);
    }

    @Override
    public void run() {
        if (queueManager.getQueue(queue.getPlayer()) == null || elo - range <= 0) {
            this.cancel();
            return;
        }

        this.range += RANGE_INCREASE;

        this.sendMSG(elo);
    }

    private void sendMSG(int elo) {
        this.actionBar.setMessage(LanguageManager.getString("QUEUES.ELO-RANGE")
                .replaceAll("%from%", String.valueOf(elo - range))
                .replaceAll("%to%", String.valueOf(elo + range)));
    }

}
