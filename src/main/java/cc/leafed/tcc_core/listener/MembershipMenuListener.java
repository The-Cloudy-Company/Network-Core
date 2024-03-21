package cc.leafed.tcc_core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MembershipMenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if(!e.getView().getTitle().equalsIgnoreCase("~ MY MEMBERSHIP ~")) { return; }
        try {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            if(meta.getDisplayName().equals("Renew my membership")) {
                // Send the member to the renewal system
                p.sendMessage("§6Renew your Membership: https://thecloudyco.com/profile/renew");
            }
            if(meta.getDisplayName().equals("Upgrade to Premium!")) {
                // Send them to the upgrade system
                p.sendMessage("§6Renew your Membership: https://thecloudyco.com/profile/upgrade");
            }
            if(meta.getDisplayName().equals("Downgrade Membership")) {
                // Send them to the reverse upgrade system
                p.sendMessage("§6Renew your Membership: https://thecloudyco.com/profile/downgrade");
            }
            if(meta.getDisplayName().equals("Cancel my membership")) {
                // We dont cancel memberships
                p.sendMessage("§6Renew your Membership: https://thecloudyco.com/profile/cancel");
            }
        } catch(NullPointerException ex) {}
    }
}
