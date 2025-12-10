package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.Arenas.Interface.DisplayArena;
import dev.nandi0813.practice.Manager.Backend.ConfigFile;

public class ArenaFile extends ConfigFile {

    public ArenaFile(DisplayArena arena) {
        super("/arenas/", arena.getName().toLowerCase());

        saveFile();
        reloadFile();
    }

    @Override
    public void setData() {
    }

    @Override
    public void getData() {
    }

}
