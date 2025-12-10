package dev.nandi0813.practice.Manager.Arena.Arenas.Interface;

import dev.nandi0813.practice.Manager.Arena.ArenaFile;
import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.Arena.ArenaType;
import dev.nandi0813.practice.Manager.Arena.Arenas.Arena;
import dev.nandi0813.practice.Manager.Arena.Arenas.ArenaCopy;
import dev.nandi0813.practice.Manager.Arena.Arenas.FFAArena;
import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.FFA.FFA.FFA;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.Setup.Arena.ArenaSetupManager;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class DisplayArena extends NormalArena {

    private static final boolean DEFAULT_ICON = ConfigManager.getBoolean("ARENA.DEFAULT-ICON.ENABLED");
    private static final GUIItem DEFAULT_ICON_ITEM = ConfigManager.getGuiItem("ARENA.DEFAULT-ICON.ICON");

    protected final ArenaFile arenaFile;
    protected ItemStack icon;
    @Setter
    protected String displayName;

    protected boolean enabled;
    protected final ArenaType type;
    @Setter
    protected boolean build;
    @Setter
    protected List<NormalLadder> assignedLadders = new ArrayList<>();

    protected DisplayArena(String name, ArenaType type) {
        super(name);

        this.type = type;
        this.displayName = name;

        arenaFile = new ArenaFile(this);
    }

    public void setIcon(final ItemStack icon) {
        if (icon == null || icon.getType().equals(Material.AIR)) {
            return;
        }

        this.icon = icon.clone();

        if (icon.hasItemMeta())
            this.displayName = StringUtil.CC(icon.getItemMeta().getDisplayName());
        else
            this.displayName = name;
    }

    public ItemStack getIcon() {
        if (this.icon == null) {
            if (DEFAULT_ICON) {
                GUIItem guiItem = DEFAULT_ICON_ITEM.cloneItem();
                guiItem.replaceAll("%arena%", this.name);
                return guiItem.get().clone();
            }
            return null;
        }
        return this.icon.clone();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (!enabled) {
            if (this instanceof FFAArena) {
                FFA ffa = ((FFAArena) this).getFfa();
                if (ffa != null) {
                    ffa.close(LanguageManager.getString("FFA.ALL-LADDERS-DISABLED"));
                }
            }
        }

        if (enabled && ArenaManager.LOAD_CHUNKS) {
            this.loadChunks();

            if (this instanceof Arena arena) {
                if (arena.getCopies() != null && !arena.getCopies().isEmpty()) {
                    for (ArenaCopy copy : arena.getCopies()) {
                        copy.loadChunks();
                    }
                }
            }
        }

        if (ArenaSetupManager.getInstance().getArenaSetupGUIs().containsKey(this)) {
            ArenaSetupManager.getInstance().getArenaSetupGUIs().get(this).get(GUIType.Arena_Main).update();
        }

        GUI arenaSummary = GUIManager.getInstance().searchGUI(GUIType.Arena_Summary);
        if (arenaSummary != null) {
            arenaSummary.update();
        }
    }

    public abstract void setData();

    public abstract void getData();

    public abstract boolean isReadyToEnable();

    public abstract List<NormalLadder> getAssignableLadders();

    public abstract boolean deleteData();

}
