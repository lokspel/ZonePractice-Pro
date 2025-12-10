package dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.Juggernaut;

import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventData;
import dev.nandi0813.practice.Module.Interfaces.KitData;
import dev.nandi0813.practice.Module.Util.ClassImport;
import lombok.Getter;

import java.io.IOException;

@Getter
public class JuggernautData extends EventData {

    private final KitData juggernautKitData = ClassImport.createKitData();
    private final KitData playerKitData = ClassImport.createKitData();

    public JuggernautData() {
        super(EventType.JUGGERNAUT);
    }

    @Override
    protected void setCustomData() {
        juggernautKitData.saveData(this.config, "juggernaut-kit");
        playerKitData.saveData(this.config, "player-kit");
    }

    @Override
    protected void getCustomData() {
        juggernautKitData.getData(this.config, "juggernaut-kit");
        playerKitData.getData(this.config, "player-kit");
    }

    @Override
    protected void enable() throws IOException {
        if (!juggernautKitData.isSet()) {
            throw new IOException("Juggernaut kit data is not set.");
        } else if (!playerKitData.isSet()) {
            throw new IOException("Player kit data is not set.");
        }
    }

}
