package com.codris.features;

import com.codris.Utilities;
import com.google.common.base.Joiner;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

import static com.codris.features.GuildCommand.cooldown;
import static com.codris.features.GuildCommand.getDividerAqua;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("gchat");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            handle((ProxiedPlayer) sender, args);
        } else {
            sender.sendMessage(Utilities.trans("&cThis command can only be executed by a player!"));
        }
    }

    public static void handle(ProxiedPlayer player, String[] args) {
        if (cooldown.containsKey(player.getUniqueId())) {
            if (System.currentTimeMillis() - cooldown.get(player.getUniqueId()) < 1000) {
                player.sendMessage("§cYou are sending commands too fast!");
                return;
            }
        }
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
        if (Guild.inGuild(player)) {
            Guild guild = Guild.getGuildFromPlayer(player);

            String toAdd = "";
            if (guild.getLeader().equals(player.getUniqueId().toString())) {
                toAdd = "§" + guild.getTagColor() + "[GM]";
            }
            if (guild.getMembers().contains(player.getUniqueId().toString())) {
                toAdd = "§" + guild.getTagColor() + "[M]";
            }
            if (guild.getOfficer().contains(player.getUniqueId().toString())) {
                toAdd = "§" + guild.getTagColor() + "[OF]";
            }

            String finalToAdd = toAdd;
            guild.getOnlinePlayers().forEach(onlinePlayer -> {
                onlinePlayer.sendMessage("§2Guild > " + Utilities.getRankFromPlayer(player) + player.getName() + " " + finalToAdd + "§f: " + Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length)));
            });
        } else {
            player.sendMessage(getDividerAqua());
            player.sendMessage("§cYou must be in a guild to use this command!");
            player.sendMessage(getDividerAqua());
        }
    }
}
