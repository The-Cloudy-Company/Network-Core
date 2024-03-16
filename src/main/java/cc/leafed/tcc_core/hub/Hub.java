package cc.leafed.tcc_core.hub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Hub implements Listener {
    @EventHandler
    public void onSpawn(PlayerJoinEvent e) {
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setItem(4, getPortal());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setItem(4, getPortal());
    }

    @EventHandler
    public void onItemMove(InventoryClickEvent e) {
        if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Server Portal")) {
                Bukkit.dispatchCommand(e.getPlayer(), "portal");
            }
        } catch(NullPointerException ex) {}
    }

    private ItemStack getPortal() {
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta meta = clock.getItemMeta();
        meta.setDisplayName("§6Server Portal");
        clock.setItemMeta(meta);
        return clock;
    }
}
