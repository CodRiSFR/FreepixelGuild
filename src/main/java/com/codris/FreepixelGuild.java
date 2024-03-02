package com.codris;

import com.codris.database.GuildDatabase;
import com.codris.features.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public final class FreepixelGuild extends Plugin {
    public static String MONGO_URI = "";

    @Override
    public void onEnable() {
        System.out.println("------------------------------------");
        System.out.println("Guilds enabled!");
        System.out.println("------------------------------------");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GuildCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatCommand());
        getProxy().registerChannel("freepixelguilds:tag");
        ProxyServer.getInstance().getPluginManager().registerListener(this, new GuildListener());

        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            MONGO_URI = configuration.getString("mongo-uri");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new GuildDatabase().connect(MONGO_URI);
        new Thread(() -> {
            GuildDatabase.collection.find().into(new ArrayList<>()).forEach(guild -> {
                Guild g = new Guild(UUID.fromString(guild.getString("_id")), guild.getString("leader"), guild.getString("name"), (guild.containsKey("displayName") ? guild.getString("displayName") : guild.getString("name")), guild.getString("motd"), guild.getString("tag"), guild.get("tagColor", "7"), (ArrayList<String>) guild.get("members"), (ArrayList<String>) guild.get("officer"), guild.getLong("mutedUntil"), (guild.containsKey("experience") ? guild.getLong("experience") : 0), (guild.containsKey("expHistory") ? (Map<String, Long>) guild.get("expHistory") : new HashMap<>()),
                        guild.get("discord", "none"), guild.getBoolean("open", false), guild.getBoolean("publicDiscord", false), guild.get("description", "none"));
                if (!guild.containsKey("expHistory")) {
                    HashMap<String, Long> expHistory = new HashMap<>();
                    g.getAllPlayers().forEach((p) -> {
                        expHistory.put(p, 0L);
                    });
                    g.setExpHistory(expHistory);
                }
                if (!guild.containsKey("displayName")) g.setName(guild.getString("name"));
                if (!guild.containsKey("tagColor")) g.setTagColor("7");
                if (!guild.containsKey("experience")) g.setExperience(0);
                if (!guild.containsKey("description")) g.setDescription("none");
                if (!guild.containsKey("open")) g.setOpen(false);
                if (!guild.containsKey("publicDiscord")) g.setPublicDiscord(false);
                if (!guild.containsKey("discord")) g.setDiscord("none");
                if (!Color.getUnlockedColors(UUID.fromString(g.getLeader()), g).contains(Color.getTagColorByCode(g.getTagColor()).name()) && !(Color.getTagColorByCode(g.getTagColor()).getRequirement() == null))
                    g.setTagColor("7");
            });
        }).start();
    }

    @Override
    public void onDisable() {
        System.out.println("------------------------------------");
        System.out.println("Guilds disabled!");
        System.out.println("------------------------------------");
    }
}
