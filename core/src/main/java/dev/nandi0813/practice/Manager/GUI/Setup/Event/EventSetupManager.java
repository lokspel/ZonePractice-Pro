package dev.nandi0813.practice.Manager.GUI.Setup.Event;

import dev.nandi0813.practice.Manager.Arena.Util.ArenaWorldUtil;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventData;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.GUI.Setup.Event.EventSettings.EventMainGui;
import dev.nandi0813.practice.Manager.GUI.Setup.Event.EventSettings.SettingsGui;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EventSetupManager implements Listener {

    private static EventSetupManager instance;

    public static EventSetupManager getInstance() {
        if (instance == null)
            instance = new EventSetupManager();
        return instance;
    }

    @Getter
    public static final Map<ItemStack, EventData> eventMarkerList = new HashMap<>();
    @Getter
    private final Map<EventData, Map<GUIType, GUI>> eventSetupGUIs = new HashMap<>();

    public EventSetupManager() {
        Bukkit.getPluginManager().registerEvents(this, ZonePractice.getInstance());
    }

    public void buildEventSetupGUIs(EventData eventData) {
        Map<GUIType, GUI> guis = new HashMap<>();

        guis.put(GUIType.Event_Main, new EventMainGui(eventData));
        guis.put(GUIType.Event_Settings, new SettingsGui(eventData));

        eventSetupGUIs.put(eventData, guis);
    }

    public void loadGUIs() {
        GUIManager.getInstance().addGUI(new EventSummaryGui());

        for (EventData eventData : EventManager.getInstance().getEventData().values())
            buildEventSetupGUIs(eventData);
    }

    @EventHandler
    public void onEventCornerMarkerUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Profile profile = ProfileManager.getInstance().getProfile(player);

        Action action = e.getAction();
        ItemStack item = e.getItem();
        Block block = e.getClickedBlock();

        switch (profile.getStatus()) {
            case MATCH:
            case FFA:
            case EVENT:
                return;
        }

        if (!player.hasPermission("zpp.setup")) return;
        if (!action.equals(Action.LEFT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (item == null) return;
        if (block == null) return;

        if (!eventMarkerList.containsKey(item)) return;
        EventData eventData = eventMarkerList.get(item);
        if (eventData == null) return;

        e.setCancelled(true);
        if (eventData.isEnabled()) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.EVENT.CANT-EDIT-ENABLED"));
            return;
        }

        if (!player.getWorld().equals(ArenaWorldUtil.getArenasWorld())) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.EVENT.ARENAS-WORLD"));
            return;
        }

        try {
            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                eventData.setCuboidLoc1(block.getLocation());
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.EVENT.SET-FIRST-CORNER").replaceAll("%event%", eventData.getType().getName()));
            } else {
                eventData.setCuboidLoc2(block.getLocation());
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.SETUP.EVENT.SET-SECOND-CORNER").replaceAll("%event%", eventData.getType().getName()));
            }
        } catch (Exception exception) {
            Common.sendMMMessage(player, "<red>" + exception.getMessage());
            return;
        }

        eventSetupGUIs.get(eventData).get(GUIType.Event_Main).update();
    }

}
