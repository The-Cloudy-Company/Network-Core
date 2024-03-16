package cc.leafed.tcc_core.cmd;

import cc.leafed.tcc_core.Core;
import cc.leafed.tcc_core.common.StaffMessage;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.thecloudyco.cc.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class StaffChatCmd implements CommandExecutor, PluginMessageListener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0 || args == null) {
            sender.sendMessage("§cUsage: /s <Message> - Send a message in staff chat");
            return true;
        }
        Gson gson = new Gson();
        Player player = (Player) sender;
        String message = StringUtil.combine(args, 0);
        String serverName = Core.getCore().getConfig().getString("server.name");

        // First send the message to all staff members currently on this server
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(pl.hasPermission("thecloudyco.staff")) {
                pl.sendMessage("§4§lStaff " + serverName + " §b" + player.getName() + " : §c" + message);
            }
        }

        // Then construct a StaffMessage object
        StaffMessage object = new StaffMessage(player.getName(), serverName, message);
        //Bukkit.getLogger().info(gson.toJson(object));

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("StaffMsg");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(gson.toJson(object)); // You can do anything you want with msgout
        } catch (IOException exception){
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        player.sendPluginMessage(Core.getCore(), "BungeeCord", out.toByteArray());
        return true;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        short len = in.readShort();
        byte[] msgbytes = new byte[len];
        in.readFully(msgbytes);

        DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
        try {
            String somedata = msgin.readUTF(); // Read the data in the same way you wrote it
            if(subChannel.equalsIgnoreCase("StaffMsg")) {
                Gson gson = new Gson();
                StaffMessage object = gson.fromJson(somedata, StaffMessage.class);

                for(Player pl : Bukkit.getOnlinePlayers()) {
                    if(pl.hasPermission("thecloudyco.staff")) {
                        pl.sendMessage("§4§lStaff " + object.getServer() + " §b" + object.getSender() + " : §c" + object.getMessage());
                    }
                }
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
