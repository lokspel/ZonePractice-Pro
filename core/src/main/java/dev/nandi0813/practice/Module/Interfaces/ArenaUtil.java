package dev.nandi0813.practice.Module.Interfaces;

import dev.nandi0813.practice.Manager.Arena.Arenas.Interface.BasicArena;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import org.bukkit.block.Block;

public interface ArenaUtil {

    boolean turnsToDirt(Block block);

    boolean containsDestroyableBlock(Ladder ladder, Block block);

    void loadArenaChunks(BasicArena arena);

}
