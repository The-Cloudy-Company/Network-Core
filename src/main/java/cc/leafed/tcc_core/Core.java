package cc.leafed.tcc_core;

import cc.leafed.tcc_core.cmd.IPCmd;
import cc.leafed.tcc_core.cmd.PortalCmd;
import cc.leafed.tcc_core.cmd.StaffChatCmd;
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

        // Register BungeeCord messaging channels
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", ipCMD);
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", staffChatCmd);

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

        // Initialize Event Listeners
        Bukkit.getPluginManager().registerEvents(new MembershipListener(), this);
        Bukkit.getPluginManager().registerEvents(new PortalListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        core = null;
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    public static Core getCore() {
        return core;
    }
}
