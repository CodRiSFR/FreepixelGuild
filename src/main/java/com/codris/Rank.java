package com.codris;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

@Getter
public enum Rank {

    DEFAULT("system.default", "&7"),
    BETA("system.bt", "&d[BETA TESTER]"),
    VIP("system.vip", "&a[VIP]"),
    VIPPLUS("system.vipp", "&a[VIP&6+&a]"),
    MVP("system.mvp", "&b[MVP]"),
    MVPPLUS("system.mvpp", "&b[MVP&c+&b]"),
    MVPPLUSPLUS("system.mvppp", "&6[MVP&c++&6]"),
    YOUTUBE("system.yt", "&c[&fYOUTUBE&c]"),
    JRHELPER("system.jrhelper", "&9[JR HELPER]"),
    HELPER("system.helper", "&9[HELPER]"),
    MOD("system.mod", "&2[MOD]"),
    GAMEMASTER("system.gm", "&2[GM]"),
    BUILD("system.build", "&3[BUILD TEAM]"),
    BUILDPLUS("system.buildp", "&3[BUILD TEAM&c+&3]"),
    ADMIN("system.admin", "&c[ADMIN]"),
    OWNER("system.owner", "&c[OWNER]");

    public static final Rank[] RANKS;
    private final String permission;
    private final String prefix;

    Rank(String permission, String prefix) {
        this.permission = permission;
        this.prefix = prefix;
    }

    public Rank getPlayerRank(String permission) {
        for (Rank r : values()) {
            if (r.permission.equalsIgnoreCase(permission))
                return r;
        }
        return DEFAULT;
    }


    public int getPriority() {
        return ordinal();
    }

    public boolean isBelowOrEqual(Rank rank) {
        return (ordinal() <= rank.ordinal());
    }

    public boolean isAboveOrEqual(Rank rank) {
        return (ordinal() >= rank.ordinal());
    }

    public static Rank getRankOrDefault(int i) {
        Rank rank;
        if (i == 0) {
            rank = DEFAULT;
        } else if (i == 1) {
            rank = VIP;
        } else if (i == 2) {
            rank = VIPPLUS;
        } else if (i == 3) {
            rank = MVP;
        } else if (i == 4) {
            rank = MVPPLUS;
        } else if (i == 5) {
            rank = MVPPLUSPLUS;
        } else if (i == 6) {
            rank = HELPER;
        } else if (i == 7) {
            rank = MOD;
        } else if (i == 8) {
            rank = ADMIN;
        } else if (i == 10) {
            rank = OWNER;
        } else {
            rank = DEFAULT;
        }
        return rank;
    }

    public boolean hasRank(Rank requiredRank) {
        return (getPriority() >= requiredRank.getPriority());
    }

    public boolean isStaff() {
        return (ordinal() >= HELPER.ordinal());
    }

    public static Rank findRankByPermission(final ProxiedPlayer player) {
        return Arrays.stream(RANKS).filter(rank -> player.hasPermission(rank.permission)).findFirst().orElse(DEFAULT);
    }

    static {
        RANKS = values();
    }
}
