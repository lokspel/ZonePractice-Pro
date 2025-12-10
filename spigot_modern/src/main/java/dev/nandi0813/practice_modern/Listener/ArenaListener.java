package dev.nandi0813.practice_modern.Listener;

import dev.nandi0813.practice.Manager.Arena.Util.ArenaWorldUtil;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import static dev.nandi0813.practice.Manager.Arena.ArenaManager.LOADED_CHUNKS;
import static dev.nandi0813.practice.Manager.Arena.ArenaManager.LOAD_CHUNKS;

public class ArenaListener implements Listener {

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (LOAD_CHUNKS) {
            if (LOADED_CHUNKS.contains(e.getChunk())) {
                Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () ->
                        e.getChunk().load(true), 1L);
            }
        }
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        if (e.getWorld() == ArenaWorldUtil.getArenasWorld()) {
            e.setCancelled(true);
            return;
        }

        if (e.getWorld() == ArenaWorldUtil.getArenasCopyWorld()) {
            e.setCancelled(true);
            return;
        }

        if (ServerManager.getLobby() != null && ServerManager.getLobby().getWorld() == e.getWorld()) {
            e.setCancelled(true);
        }
    }

}
