package dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface;

import org.bukkit.configuration.file.YamlConfiguration;

public interface CustomConfig {

    void setCustomConfig(YamlConfiguration config);

    void getCustomConfig(YamlConfiguration config);

}
