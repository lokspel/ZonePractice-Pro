package dev.nandi0813.practice.Manager.Fight.Match.Enum;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Util.Common;
import lombok.Getter;

public enum MatchType {

    DUEL(ConfigManager.getConfig().getString("MATCH-SETTINGS.MATCH-TYPE-NAMES.DUEL"), "DUEL"),
    PARTY_FFA(ConfigManager.getConfig().getString("MATCH-SETTINGS.MATCH-TYPE-NAMES.PARTY_FFA"), "PARTY-FFA"),
    PARTY_SPLIT(ConfigManager.getConfig().getString("MATCH-SETTINGS.MATCH-TYPE-NAMES.PARTY_SPLIT"), "PARTY-SPLIT"),
    PARTY_VS_PARTY(ConfigManager.getConfig().getString("MATCH-SETTINGS.MATCH-TYPE-NAMES.PARTY_VS_PARTY"), "PARTY-VS-PARTY");

    private final String name;
    @Getter
    private final String pathName;

    MatchType(String name, String pathName) {
        this.name = name;
        this.pathName = pathName;
    }

    public String getName(boolean miniMessageFormat) {
        if (miniMessageFormat)
            return Common.serializeNormalToMMString(this.name);
        return this.name;
    }

}
