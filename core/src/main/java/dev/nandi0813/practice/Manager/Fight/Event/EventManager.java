package dev.nandi0813.practice.Manager.Fight.Event;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Brackets.Brackets;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Brackets.BracketsData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Brackets.BracketsListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Sumo.Sumo;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Sumo.SumoData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Sumo.SumoListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.LMS.LMS;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.LMS.LMSData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.LMS.LMSListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.OITC.OITC;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.OITC.OITCData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.OITC.OITCListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.Splegg.Splegg;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.Splegg.SpleggData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.FFA.Splegg.SpleggListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.Juggernaut.Juggernaut;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.Juggernaut.JuggernautData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.Juggernaut.JuggernautListener;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.TnTTag.TNTTag;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.TnTTag.TNTTagData;
import dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.TnTTag.TNTTagListener;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.Event;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventData;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventListenerInterface;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIs.EventHostGui;
import dev.nandi0813.practice.Manager.GUI.Setup.Event.EventSetupManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.StartUpCallback;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class EventManager {

    private static EventManager instance;

    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();
        return instance;
    }

    private final List<Event> events;
    private final Map<EventType, EventData> eventData;
    private final Map<EventType, EventListenerInterface> eventListeners;

    private final EventListener listener;

    public static final ItemStack PLAYER_TRACKER = ConfigManager.getGuiItem("EVENT.PLAYER-TRACKER").get();

    private EventManager() {
        this.events = new ArrayList<>();
        this.eventData = new HashMap<>();
        this.eventListeners = new HashMap<>();

        this.listener = new EventListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.listener, ZonePractice.getInstance());
    }

    public void loadEventData(final StartUpCallback startUpCallback) {
        Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
        {
            this.eventData.put(EventType.BRACKETS, new BracketsData());
            this.eventListeners.put(EventType.BRACKETS, new BracketsListener());

            this.eventData.put(EventType.SUMO, new SumoData());
            this.eventListeners.put(EventType.SUMO, new SumoListener());

            this.eventData.put(EventType.LMS, new LMSData());
            this.eventListeners.put(EventType.LMS, new LMSListener());

            this.eventData.put(EventType.OITC, new OITCData());
            this.eventListeners.put(EventType.OITC, new OITCListener());

            this.eventData.put(EventType.SPLEGG, new SpleggData());
            this.eventListeners.put(EventType.SPLEGG, new SpleggListener());

            this.eventData.put(EventType.JUGGERNAUT, new JuggernautData());
            this.eventListeners.put(EventType.JUGGERNAUT, new JuggernautListener());

            this.eventData.put(EventType.TNTTAG, new TNTTagData());
            this.eventListeners.put(EventType.TNTTAG, new TNTTagListener());

            for (EventData data : this.eventData.values()) {
                data.getData();
            }

            Bukkit.getScheduler().runTask(ZonePractice.getInstance(), startUpCallback::onLoadingDone);
        });
    }

    public void loadGUIs() {
        GUIManager.getInstance().addGUI(new EventHostGui());
        EventSetupManager.getInstance().loadGUIs();
    }

    public void endEvents() {
        for (Event event : events) {
            event.forceEnd(null);
        }
    }

    public void saveEventData() {
        for (EventData data : eventData.values()) {
            data.setData();
        }
    }

    public void startEvent(Player starter, EventType eventType) {
        if (this.isEventLive(eventType)) {
            if (starter != null)
                Common.sendMMMessage(starter, LanguageManager.getString("EVENT.CANT-START-EVENT").replaceAll("%event%", eventType.getName()));
            else
                Common.sendConsoleMMMessage(LanguageManager.getString("EVENT.CANT-START-EVENT").replaceAll("%event%", eventType.getName()));

            return;
        }

        Event event = switch (eventType) {
            case LMS -> new LMS(starter, (LMSData) eventData.get(EventType.LMS));
            case OITC -> new OITC(starter, (OITCData) eventData.get(EventType.OITC));
            case TNTTAG -> new TNTTag(starter, (TNTTagData) eventData.get(EventType.TNTTAG));
            case BRACKETS -> new Brackets(starter, (BracketsData) eventData.get(EventType.BRACKETS));
            case SUMO -> new Sumo(starter, (SumoData) eventData.get(EventType.SUMO));
            case SPLEGG -> new Splegg(starter, (SpleggData) eventData.get(EventType.SPLEGG));
            case JUGGERNAUT -> new Juggernaut(starter, (JuggernautData) eventData.get(EventType.JUGGERNAUT));
        };

        events.add(event);
        event.startQueue();
    }

    public boolean isEventLive(EventType eventType) {
        for (Event event : events) {
            if (event.getType().equals(eventType)) {
                return true;
            }
        }
        return false;
    }

    public List<EventData> getEnabledEvents() {
        List<EventData> enabledEvents = new ArrayList<>();
        for (EventData eventData : eventData.values()) {
            if (eventData.isEnabled()) {
                enabledEvents.add(eventData);
            }
        }
        return enabledEvents;
    }

    public Event getEventByPlayer(Player player) {
        for (Event event : events) {
            if (event.getPlayers().contains(player)) {
                return event;
            }
        }
        return null;
    }

    public Event getEventBySpectator(Player player) {
        for (Event event : events) {
            if (event.getSpectators().contains(player)) {
                return event;
            }
        }
        return null;
    }

}
