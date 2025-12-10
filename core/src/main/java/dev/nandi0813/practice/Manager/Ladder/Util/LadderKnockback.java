package dev.nandi0813.practice.Manager.Ladder.Util;

import dev.nandi0813.practice.Manager.Ladder.Enum.KnockbackType;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import lombok.Setter;
import pt.foxspigot.jar.knockback.KnockbackModule;
import xyz.refinedev.spigot.api.knockback.KnockbackAPI;
import xyz.refinedev.spigot.knockback.KnockbackProfile;

@Getter
@Setter
public class LadderKnockback {

    private KnockbackType knockbackType;
    private KnockbackProfile carbonKnockbackProfile = null;
    private pt.foxspigot.jar.knockback.KnockbackProfile foxspigotKnockbackProfile = null;

    public LadderKnockback() {
        switch (ZonePractice.getKnockbackController()) {
            case CARBON:
                carbonKnockbackProfile = KnockbackAPI.getInstance().getDefaultProfile();
                return;
            case FOX_SPIGOT:
                foxspigotKnockbackProfile = KnockbackModule.getDefault();
                return;
            default:
                knockbackType = KnockbackType.DEFAULT;
        }
    }

    public LadderKnockback(LadderKnockback ladderKnockback) {
        super();
        this.knockbackType = ladderKnockback.knockbackType;
        this.carbonKnockbackProfile = ladderKnockback.carbonKnockbackProfile;
        this.foxspigotKnockbackProfile = ladderKnockback.foxspigotKnockbackProfile;
    }

    public void get(final String knockbackValue) {
        switch (ZonePractice.getKnockbackController()) {
            case CARBON:
                this.carbonKnockbackProfile = KnockbackAPI.getInstance().getProfile(knockbackValue);

                if (this.carbonKnockbackProfile == null)
                    this.carbonKnockbackProfile = KnockbackAPI.getInstance().getDefaultProfile();
                break;
            case FOX_SPIGOT:
                this.foxspigotKnockbackProfile = KnockbackModule.getByName(knockbackValue);

                if (this.foxspigotKnockbackProfile == null)
                    this.foxspigotKnockbackProfile = KnockbackModule.getDefault();
                break;
            default:
                KnockbackType knockback;
                try {
                    knockback = KnockbackType.valueOf(knockbackValue);
                } catch (IllegalArgumentException e) {
                    Common.sendConsoleMMMessage("<red>Invalid knockback type: " + knockbackValue + ". Defaulting to DEFAULT.");
                    knockback = KnockbackType.DEFAULT;
                }

                knockbackType = knockback;
                break;
        }
    }

    public String get() {
        return switch (ZonePractice.getKnockbackController()) {
            case CARBON -> carbonKnockbackProfile.getName();
            case FOX_SPIGOT -> foxspigotKnockbackProfile.title;
            default -> knockbackType.toString();
        };
    }

    public boolean isDefault() {
        return switch (ZonePractice.getKnockbackController()) {
            case CARBON ->
                    carbonKnockbackProfile == null || carbonKnockbackProfile == KnockbackAPI.getInstance().getDefaultProfile();
            case FOX_SPIGOT ->
                    foxspigotKnockbackProfile == null || foxspigotKnockbackProfile == KnockbackModule.getDefault();
            default -> knockbackType == KnockbackType.DEFAULT;
        };
    }

}
