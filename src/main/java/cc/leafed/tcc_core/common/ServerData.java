package cc.leafed.tcc_core.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ServerData {

    /**
     * ServerData Structure on The Cloudy Company Network
     * @Author: Atticus Zambrana
     */

    // The name of the server on the server selection gui
    private String name;

    // The name of the server configured in the proxy server
    private String velo_name;

    // The lore of the itemstack in the server selection gui
    private String lore;

    // Position of the itemstack in the server selection gui
    private int position;

    // ItemStack that should appear in the server selection gui
    private Material itemstack;

    // How many players should be allowed to connect through Portal (You can still connect to it manually using commands)
    private int max_players;

    // Weather Portal should only allow Staff members to see and connect to the server
    private int staff_only;
    public ServerData(String name, String velo_name, String lore, int position, Material itemstack, int max_players, int staff_only) {
        this.name = name;
        this.velo_name = velo_name;
        this.lore = lore;
        this.position = position;
        this.max_players = max_players;
        this.staff_only = staff_only;
        this.itemstack = itemstack;
    }

    public String getName() {
        return name;
    }
    public String getProxyName() {
        return velo_name;
    }
    public List<String> getLore() {
        List<String> l = new ArrayList<>();
        l.add(ChatColor.RESET + lore);
        l.add("");
        l.add(ChatColor.RESET + "" + ChatColor.BLACK + velo_name);
        return l;
    }
    public int getPosition() {
        return position;
    }
    public int getMaxPlayers() {
        return max_players;
    }
    public boolean isStaffOnly() {
        return staff_only == 1;
    }
    public Material getItemStack() {
        return itemstack;
    }
}
