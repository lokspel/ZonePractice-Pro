package dev.nandi0813.practice_1_8_8.Interfaces;

import dev.nandi0813.practice.Manager.Arena.Arenas.ArenaCopy;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Util.Cuboid;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class ArenaCopyUtil extends dev.nandi0813.practice.Module.Interfaces.ArenaCopyUtil {

    @Override
    protected void copyBlock(Block oldBlock, Block newBlock) {
        newBlock.setType(oldBlock.getType());

        BlockState oldState = oldBlock.getState();
        BlockState newState = newBlock.getState();

        newState.setData(oldState.getData().clone());
        newState.update();

        newBlock.setBiome(oldBlock.getBiome());
    }

    @Override
    protected void copyArena(Profile profile, ArenaCopy arenaCopy, Cuboid copyFrom, Location reference, Location newLocation) {
        copyNormal(profile, arenaCopy, copyFrom, reference, newLocation);
    }

    @Override
    public void deleteArena(final String arena, final Cuboid cuboid) {
        deleteNormal(arena, cuboid);
    }

}
