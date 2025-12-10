package dev.nandi0813.practice.Manager.GUI.GUIs;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.EventManager;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Module.Interfaces.ItemCreateUtil;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.InventoryUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EventHostGui extends GUI {

    private final Map<Integer, EventType> eventSlots = new HashMap<>();

    public EventHostGui() {
        super(GUIType.Event_Host);
        this.gui.put(1, InventoryUtil.createInventory(GUIFile.getString("GUIS.EVENT-HOST.TITLE"), 1));

        build();
    }

    @Override
    public void build() {
        update();
    }

    @Override
    public void update() {
        eventSlots.clear();
        gui.get(1).clear();

        for (EventType eventType : EventType.values()) {
            if (EventManager.getInstance().getEventData().get(eventType).isEnabled()) {
                int slot = gui.get(1).firstEmpty();
                eventSlots.put(slot, eventType);

                gui.get(1).setItem(slot, ItemCreateUtil.hideItemFlags(eventType.getIcon()));
            }
        }
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Profile profile = ProfileManager.getInstance().getProfile(player);
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (e.getView().getTopInventory().getSize() > slot) {
            if (eventSlots.containsKey(slot)) {
                EventType eventType = eventSlots.get(slot);

                if (!EventManager.getInstance().getEvents().isEmpty() && !ConfigManager.getBoolean("EVENT.MULTIPLE")) {
                    Common.sendMMMessage(player, LanguageManager.getString("EVENT.ONLY-ONE-EVENT"));
                    return;
                }

                if (!player.hasPermission("zpp.event.host." + eventType.name().toLowerCase()) && !player.hasPermission("zpp.event.host.all")) {
                    Common.sendMMMessage(player, LanguageManager.getString("EVENT.CANT-HOST-EVENT").replaceAll("%event%", eventType.getName()));
                    return;
                }

                if (profile.getEventStartLeft() <= 0) {
                    Common.sendMMMessage(player, LanguageManager.getString("EVENT.CANT-HOST-EVENT-TODAY"));
                    return;
                }

                EventManager.getInstance().startEvent(player, eventType);
                player.closeInventory();
            }
        }
    }

}
