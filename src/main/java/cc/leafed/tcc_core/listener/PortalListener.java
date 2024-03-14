package cc.leafed.tcc_core.listener;

import cc.leafed.tcc_core.TCC_Core;
import cc.leafed.tcc_core.common.ServerData;
import cc.leafed.tcc_core.common.ServerUtil;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;

public class PortalListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent ev) {
        if(!ev.getView().getTitle().equalsIgnoreCase("~ NETWORK PORTAL ~")) { return; }
        try {
            ev.setCancelled(true);

            ItemStack item = ev.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String serverName = meta.getLore().get(2).substring(2);// should be the server name?
            ServerData serverData = null;
            try {
                serverData = ServerUtil.getServerData(serverName);
            } catch (SQLException e) {/* idk */ }

            // Verify if the server is staff only
            if(serverData.isStaffOnly()) {
                if(!((Player)ev.getWhoClicked()).hasPermission("thecloudyco.staff")) {
                    ((Player)ev.getWhoClicked()).sendMessage("§cYou are not allowed to join this server.");
                    return;
                }
            }

            ((Player)ev.getWhoClicked()).sendMessage("§6Connecting you to " + serverName + "...");

            // Send the player over to the server after the checks
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            ((Player)ev.getWhoClicked()).sendPluginMessage(TCC_Core.getCore(), "BungeeCord", out.toByteArray());

        } catch(NullPointerException e) {}
    }
}
