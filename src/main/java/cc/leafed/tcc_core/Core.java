package cc.leafed.tcc_core;

import cc.leafed.tcc_core.cmd.*;
import cc.leafed.tcc_core.hub.Hub;
import cc.leafed.tcc_core.listener.MembershipMenuListener;
import cc.leafed.tcc_core.listener.PlayerListener;
import cc.leafed.tcc_core.listener.PortalListener;
import cc.leafed.tcc_core.membership.MembershipListener;
import com.thecloudyco.cc.database.CloverDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Core extends JavaPlugin {

    private static Core core;

    @Override
    public void onEnable() {
        // Plugin startup logic
        core = this;

        // ?
        IPCmd ipCMD = new IPCmd();
        StaffChatCmd staffChatCmd = new StaffChatCmd();
        FindCmd findCmd = new FindCmd();

        // Register BungeeCord messaging channels
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", ipCMD);
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", staffChatCmd);
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", findCmd);

        // Create the initial config file
        saveDefaultConfig();

        Bukkit.getLogger().info("[THE CLOUDY CO] Starting Core...");
        // Connect to the local database
        CloverDatabase.startTracker();
        try {
            CloverDatabase.connect(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Initialize Commands
        getCommand("portal").setExecutor(new PortalCmd());
        getCommand("ip").setExecutor(ipCMD);
        getCommand("sc").setExecutor(staffChatCmd);
        getCommand("find").setExecutor(findCmd);
        getCommand("skin").setExecutor(new SkinCmd());
        getCommand("membership").setExecutor(new MembershipCmd());
        getCommand("hub").setExecutor(new HubCommand());

        // Initialize Event Listeners
        Bukkit.getPluginManager().registerEvents(new MembershipListener(), this);
        Bukkit.getPluginManager().registerEvents(new PortalListener(), this);
        if(Core.getCore().getConfig().getString("server.group").equalsIgnoreCase("hub")) {
            Bukkit.getPluginManager().registerEvents(new Hub(), this);
        }
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MembershipMenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        core = null;
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public static Core getCore() {
        return core;
    }

    public static String getMyName() {
        return Core.getCore().getConfig().getString("server.name");
    }
}
