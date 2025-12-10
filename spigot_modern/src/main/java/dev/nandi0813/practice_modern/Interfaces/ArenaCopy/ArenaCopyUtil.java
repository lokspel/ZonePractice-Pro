package dev.nandi0813.practice_modern.Interfaces.ArenaCopy;

import dev.nandi0813.practice.Manager.Arena.Arenas.ArenaCopy;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.Setup.Arena.ArenaSetupManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Util.Cuboid;
import dev.nandi0813.practice.Util.SoftDependUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class ArenaCopyUtil extends dev.nandi0813.practice.Module.Interfaces.ArenaCopyUtil {

    @Override
    protected void copyBlock(Block oldBlock, Block newBlock) {
        newBlock.setType(oldBlock.getType());

        BlockState oldState = oldBlock.getState();
        BlockState newState = newBlock.getState();

        newState.setBlockData(oldState.getBlockData().clone());
        newState.update();

        newBlock.setBiome(oldBlock.getBiome());
    }

    @Override
    protected void copyArena(Profile profile, ArenaCopy arenaCopy, Cuboid copyFrom, Location reference, Location newLocation) {
        if (SoftDependUtil.isFAWE_ENABLED) {
            FaweUtil.copyFAWE(copyFrom, reference, newLocation);

            arenaCopy.getMainArena().getCopies().add(arenaCopy);
            ArenaSetupManager.getInstance().getArenaSetupGUIs().get(arenaCopy.getMainArena()).get(GUIType.Arena_Copy).update();
            ArenaSetupManager.getInstance().getArenaSetupGUIs().get(arenaCopy.getMainArena()).get(GUIType.Arena_Main).update();
            GUIManager.getInstance().searchGUI(GUIType.Arena_Summary).update();
        } else {
            this.copyNormal(profile, arenaCopy, copyFrom, reference, newLocation);
        }
    }

    @Override
    public void deleteArena(final String arena, final Cuboid cuboid) {
        if (SoftDependUtil.isFAWE_ENABLED) {
            FaweUtil.deleteFAWE(cuboid);
        } else {
            deleteNormal(arena, cuboid);
        }
    }

}
