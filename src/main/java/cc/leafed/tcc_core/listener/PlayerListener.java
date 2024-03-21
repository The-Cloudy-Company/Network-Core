package cc.leafed.tcc_core.listener;

import cc.leafed.tcc_core.Core;
import cc.leafed.tcc_core.common.StaffMessage;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerListener implements Listener {
    @EventHandler
    public void onConnect(PlayerJoinEvent e) {
        e.setJoinMessage("§8[§a+§8] §7" + e.getPlayer().getName());

        if(e.getPlayer().hasPermission("thecloudyco.staff")) {
            sendNotification(e.getPlayer(), Core.getMyName(), e.getPlayer().getName() + " left from " + Core.getMyName() + ".");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§8[§c-§8] §7" + e.getPlayer().getName());

        if(e.getPlayer().hasPermission("thecloudyco.staff")) {
            sendNotification(e.getPlayer(), Core.getMyName(), e.getPlayer().getName() + " left from " + Core.getMyName() + ".");
        }
    }

    public void sendNotification(Player player, String serverName, String message) {
        Gson gson = new Gson();
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
    }
}
