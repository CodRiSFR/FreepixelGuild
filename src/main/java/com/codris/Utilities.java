package com.codris;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.NumberFormat;
import java.util.*;

public class Utilities {
    private static final NumberFormat COMMA_FORMAT = NumberFormat.getInstance();

    public static void runAsync(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static HashMap<?, ?> sortByValue(Map<?, ?> map) {
        List<Map.Entry<?, ?>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> ((Comparable) o2.getValue()).compareTo(o1.getValue()));
        HashMap<Object, Object> result = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void delay(Runnable runnable, long delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay / 20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runnable.run();
        }).start();
    }

    public static String commaify(long l) {
        return COMMA_FORMAT.format(l);
    }

    public static String getRankFromPlayer(ProxiedPlayer player) {
        if (player.hasPermission("rank.owner"))
            return ChatColor.RED + "[OWNER]";
        if (player.hasPermission("rank.deputy"))
            return ChatColor.RED + "[DEPUTY]";
        if (player.hasPermission("rank.curator"))
            return ChatColor.RED + "[CURATOR]";
        if (player.hasPermission("rank.admin"))
            return ChatColor.RED + "[ADMIN]";
        if (player.hasPermission("rank.gm"))
            return ChatColor.DARK_GREEN + "[GM]";
        if (player.hasPermission("rank.mod"))
            return ChatColor.DARK_GREEN + "[MOD]";
        if (player.hasPermission("rank.helper"))
            return ChatColor.BLUE + "[HELPER]";
        if (player.hasPermission("rank.jrhelper"))
            return ChatColor.BLUE + "[JR.HELPER]";
        if (player.hasPermission("rank.bt"))
            return ChatColor.LIGHT_PURPLE + "[BT]";
        if (player.hasPermission("rank.sponser"))
            return ChatColor.LIGHT_PURPLE + "[SPONSER]";
        if (player.hasPermission("rank.pig"))
            return ChatColor.LIGHT_PURPLE + "[PIG]";
        if (player.hasPermission("rank.youtube"))
            return ChatColor.RED + "[" + ChatColor.WHITE + "YOUTUBE" + ChatColor.RED + "]";
        if (player.hasPermission("rank.mvp++"))
            return ChatColor.GOLD + "[MVP" + ChatColor.RED + "++" + ChatColor.GOLD + "]";
        if (player.hasPermission("rank.mvp+"))
            return ChatColor.AQUA + "[MVP" + ChatColor.RED + "+" + ChatColor.AQUA + "]";
        if (player.hasPermission("rank.mvp"))
            return ChatColor.AQUA + "[MVP]";
        if (player.hasPermission("rank.vip+"))
            return ChatColor.GREEN + "[VIP" + ChatColor.GOLD + "+" + ChatColor.GREEN + "]";
        if (player.hasPermission("rank.vip"))
            return ChatColor.GREEN + "[VIP]";
        if (player.hasPermission("rank.default"))
            return "&7";
        return null;
    }
}
