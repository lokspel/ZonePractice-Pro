package dev.nandi0813.practice.Manager.Fight.Util;

import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public enum BlockUtil {
    ;

    public static void breakBlock(Match match, Block block) {
        if (match == null) return;

        match.addBlockChange(ClassImport.createChangeBlock(block));
        block.breakNaturally();
    }

    public static void breakBlock(FFA ffa, Block block) {
        if (ffa == null) return;

        ffa.getFightChange().addBlockChange(ClassImport.createChangeBlock(block));
        block.breakNaturally();
    }

    public static MetadataValue getMetadata(Metadatable metadatable, String tag) {
        for (MetadataValue mv : metadatable.getMetadata(tag)) {
            if (mv != null && mv.getOwningPlugin() == ZonePractice.getInstance()) {
                return mv;
            }
        }
        return null;
    }

}
