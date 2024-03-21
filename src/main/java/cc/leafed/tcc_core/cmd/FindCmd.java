package cc.leafed.tcc_core.cmd;

import cc.leafed.tcc_core.Core;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class FindCmd implements CommandExecutor, PluginMessageListener {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0 || args == null) {
            commandSender.sendMessage("§cUsage: /find <Player> - Find where a player is connected to");
            return true;
        }
        Player sender = (Player) commandSender;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetPlayerServer");
        out.writeUTF(args[0]);
        sender.sendPluginMessage(Core.getCore(), "BungeeCord", out.toByteArray());
        return true;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetPlayerServer")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
            String userName = in.readUTF();
            String serverName = in.readUTF();

            player.sendMessage(ChatColor.AQUA + "" + ChatColor.RED + userName + ChatColor.AQUA + " is connected to ");
            player.sendMessage(ChatColor.RED + "" + serverName);
        }
    }
}
