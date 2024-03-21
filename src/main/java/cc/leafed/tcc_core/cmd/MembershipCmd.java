package cc.leafed.tcc_core.cmd;

import cc.leafed.tcc_core.util.DateUtil;
import cc.leafed.tcc_core.util.ItemUtil;
import com.thecloudyco.cc.membership.Membership;
import com.thecloudyco.cc.membership.MembershipUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class MembershipCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou are not allowed to run this command.");
            return false;
        }
        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, (9 * 6), "~ MY MEMBERSHIP ~");
        Membership member = null;
        try {
            member = MembershipUtil.findMembership(player.getUniqueId());
        } catch (SQLException e) {
            ItemStack error = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta meta = error.getItemMeta();
            meta.setDisplayName("§cThere was an error accessing your membership. Try again later.");
            error.setItemMeta(meta);
            inv.setItem(22, error);
        }

        // Informational Buttons
        inv.setItem(10, ItemUtil.nameItem(Material.END_CRYSTAL, "Membership #", member.getMembershipNumber()));
        inv.setItem(12, ItemUtil.nameItem(Material.END_CRYSTAL, "Membership Type", member.getType().getName()));
        inv.setItem(14, ItemUtil.nameItem(Material.END_CRYSTAL, "Email Address", member.getEmail()));
        inv.setItem(16, ItemUtil.nameItem(Material.END_CRYSTAL, "Expiration", DateUtil.getDateText(member.getExpiration())));

        // Action Buttons

        // Only show the renewal button if the member is up for renewal
        if(MembershipUtil.isUpForRenewal(member)) {
            inv.setItem(47, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Renew my membership", "Click to open!"));
            if(MembershipUtil.isExecutive(member)) {
                inv.setItem(49, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Downgrade Membership", "Click to open!"));
            } else {
                inv.setItem(49, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Upgrade to Premium!", "Click to open!"));
            }

            inv.setItem(51, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Cancel my membership", "Click to open!"));
        } else {
            if(MembershipUtil.isExecutive(member)) {
                inv.setItem(48, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Downgrade Membership", "Click to open!"));
            } else {
                inv.setItem(48, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Upgrade to Premium!", "Click to open!"));
            }
            inv.setItem(50, ItemUtil.nameItem(Material.TOTEM_OF_UNDYING, "Cancel my membership", "Click to open!"));
        }

        player.openInventory(inv);
        return true;
    }
}
