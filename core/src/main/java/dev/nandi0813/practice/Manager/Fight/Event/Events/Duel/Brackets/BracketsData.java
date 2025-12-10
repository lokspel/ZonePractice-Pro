package dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Brackets;

import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.Events.Duel.Interface.DuelEventData;
import dev.nandi0813.practice.Module.Interfaces.KitData;
import dev.nandi0813.practice.Module.Util.ClassImport;
import lombok.Getter;

import java.io.IOException;

@Getter
public class BracketsData extends DuelEventData {

    protected final KitData kitData;

    public BracketsData() {
        super(EventType.BRACKETS);
        this.kitData = ClassImport.createKitData();
    }

    @Override
    protected void setCustomData() {
        kitData.saveData(config, "kit");
    }

    @Override
    protected void getCustomData() {
        kitData.getData(config, "kit");
    }

    @Override
    protected void enable() throws IOException {
        if (!kitData.isSet()) {
            throw new IOException("Kit data is not set.");
        } else if (spawns.size() != 2) {
            throw new IOException("Spawn positions are not set. Or not equal to 2. Current size: " + spawns.size());
        }
    }

}
