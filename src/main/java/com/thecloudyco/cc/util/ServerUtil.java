package com.thecloudyco.cc.util;

import cc.leafed.tcc_core.common.ServerData;
import com.thecloudyco.cc.database.CloverDatabase;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerUtil {
    /**
     * ServerUtil has methods for managing server data
     * @Author: Atticus Zambrana
     */

    /**
     * Gives a ServerData instance for any given server by its name in the proxy server
     * @param velocityName
     * @return
     */
    public static ServerData getServerData(String velocityName) throws SQLException {
        ResultSet results = CloverDatabase.query("SELECT * FROM `network_servers` WHERE `velo_name` = '" + velocityName + "';");
        while (results.next()) {
            return new ServerData(results.getString("name"), results.getString("velo_name"), results.getString("lore"),
                    results.getInt("position"), Material.valueOf(results.getString("itemstack")), results.getInt("max_players"), results.getInt("staff_only"));
        }
        return null;
    }
}
