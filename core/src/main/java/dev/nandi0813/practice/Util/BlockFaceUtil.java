package dev.nandi0813.practice.Util;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public enum BlockFaceUtil {
    ;

    public static Vector getDirection(BlockFace face) {
        Vector direction = new Vector(face.getModX(), face.getModY(), face.getModZ());
        direction.normalize();
        return direction;
    }

}
