package dev.nandi0813.practice.Manager.Fight.Match.Enum;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum TeamEnum {

    TEAM1(
            MiniMessage.miniMessage().deserialize(ConfigManager.getConfig().getString("MATCH-SETTINGS.TEAMS.TEAM1.NAME")),
            ConfigManager.getConfig().getString("MATCH-SETTINGS.TEAMS.TEAM1.COLOR"),
            MiniMessage.miniMessage().deserialize(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM1.NAMETAG.PREFIX")),
            NamedTextColor.NAMES.valueOr(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM1.NAMETAG.NAME-COLOR").toLowerCase(), NamedTextColor.WHITE),
            MiniMessage.miniMessage().deserialize(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM1.NAMETAG.SUFFIX"))
    ),
    TEAM2(
            MiniMessage.miniMessage().deserialize(ConfigManager.getConfig().getString("MATCH-SETTINGS.TEAMS.TEAM2.NAME")),
            ConfigManager.getConfig().getString("MATCH-SETTINGS.TEAMS.TEAM2.COLOR"),
            MiniMessage.miniMessage().deserialize(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM2.NAMETAG.PREFIX")),
            NamedTextColor.NAMES.valueOr(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM2.NAMETAG.NAME-COLOR").toLowerCase(), NamedTextColor.WHITE),
            MiniMessage.miniMessage().deserialize(ConfigManager.getString("MATCH-SETTINGS.TEAMS.TEAM2.NAMETAG.SUFFIX"))
    ),
    FFA(
            Component.empty(),
            ConfigManager.getString("MATCH-SETTINGS.TEAMS.FFA.COLOR"),
            Component.empty(),
            NamedTextColor.WHITE,
            Component.empty()
    );

    private final Component name;
    private final String color;

    @Getter
    private final Component prefix;
    @Getter
    private final NamedTextColor nameColor;
    @Getter
    private final Component suffix;

    TeamEnum(Component name, String color, Component prefix, NamedTextColor nameColor, Component suffix) {
        this.name = MiniMessage.miniMessage().deserialize(color).append(name);
        this.color = color;

        this.prefix = prefix;
        this.nameColor = nameColor;
        this.suffix = suffix;
    }

    public Component getNameComponent() {
        return name;
    }

    public Component getColor() {
        return MiniMessage.miniMessage().deserialize(color);
    }

    public String getNameMM() {
        return MiniMessage.miniMessage().serialize(name);
    }

    public String getColorMM() {
        return color;
    }

}
