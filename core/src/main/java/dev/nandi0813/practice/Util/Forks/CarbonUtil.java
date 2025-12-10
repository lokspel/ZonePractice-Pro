package dev.nandi0813.practice.Util.Forks;

import xyz.refinedev.spigot.api.knockback.KnockbackAPI;
import xyz.refinedev.spigot.knockback.KnockbackProfile;

import java.util.ArrayList;
import java.util.List;

public enum CarbonUtil {
    ;

    public static KnockbackProfile getNextKnockbackProfile(KnockbackProfile knockbackProfile) {
        List<KnockbackProfile> list = new ArrayList<>(KnockbackAPI.getInstance().getProfiles());

        if (knockbackProfile != null) {
            int c = list.indexOf(knockbackProfile);

            if (list.size() - 1 == c)
                return list.get(0);
            else
                return list.get(c + 1);
        } else
            return list.get(0);
    }

}
