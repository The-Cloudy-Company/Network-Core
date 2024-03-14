package cc.leafed.tcc_core.membership;

import com.thecloudyco.cc.membership.Membership;
import com.thecloudyco.cc.membership.MembershipType;
import com.thecloudyco.cc.membership.MembershipUtil;
import org.bukkit.event.Listener;

import com.thecloudyco.cc.database.CloverDatabase;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipListener implements Listener {
    @EventHandler
    public void onConnect(AsyncPlayerPreLoginEvent ev) {

        boolean found = false;
        try {
            //ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `minecraft` = '" + ev.getPlayer().getUniqueId().toString() + "';");
            ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `minecraft` = '" + ev.getUniqueId() + "';");
            while(result.next()) {
                found = true;

                // We have verified they have a membership on file, now make sure it is active
                Membership membership = new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                        result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                        result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                        result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                        result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft"));

                if(membership.isEmployee()) {
                    // Allow login, this is a Cloudy Company Employee
                    //ev.getPlayer().sendMessage(ChatColor.GOLD + "Thank you for everything you do!");
                    return;
                }
                if(MembershipUtil.isExpired(membership)) {
                    //new BukkitRunnable() { @Override public void run() { ev.getPlayer().kickPlayer(ChatColor.RED + "Your Membership has expired. You have to renew before playing again. \n\nManage your membership at (Membership System Website)"); } }.runTask(cc.leafed.membership.Membership.getMe());
                    ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "Your Membership has expired. You have to renew before playing again. \n\nManage your membership at (Membership System Website)");
                    return;
                }
                if(membership.getType() == MembershipType.REVOKED) {
                    //new BukkitRunnable() { @Override public void run() { ev.getPlayer().kickPlayer(ChatColor.RED + "Your Membership has been revoked. \n\nPlease reach out to our member service center: atticus.zambrana@thecloudyco.com"); } }.runTask(cc.leafed.membership.Membership.getMe());
                    ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "Your Membership has been revoked. \n\nPlease reach out to our member service center: atticus.zambrana@thecloudyco.com");
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!found) {
            //new BukkitRunnable() { @Override public void run() { ev.getPlayer().kickPlayer(ChatColor.RED + "You have no active membership purchased for this account."); } }.runTask(cc.leafed.membership.Membership.getMe());
            ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You have no active membership purchased for this account.");
            return;
        }

        //ev.setJoinMessage("§8[§a+§8] §7" + ev.getPlayer().getName());
        //ev.getPlayer().getUniqueId()
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//
//            }
//        }.runTaskAsynchronously(cc.leafed.membership.Membership.getMe());
    }
}
