package dev.nandi0813.practice.Manager.Fight.Event.Events.OneVsAll.TnTTag;

import dev.nandi0813.practice.Manager.Fight.Event.Enum.EventType;
import dev.nandi0813.practice.Manager.Fight.Event.Interface.EventData;

import java.io.IOException;

public class TNTTagData extends EventData {

    public TNTTagData() {
        super(EventType.TNTTAG);
    }

    @Override
    protected void setCustomData() {
    }

    @Override
    protected void getCustomData() {
    }

    @Override
    protected void enable() throws IOException {
    }

}
