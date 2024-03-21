package cc.leafed.tcc_core.cmd;

import com.thecloudyco.cc.membership.MembershipUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.haoshoku.nick.api.NickAPI;

import java.sql.SQLException;

public class SkinCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou are not allowed to run this command.");
            return false;
        }
        
        // Make sure the member is premium level
        Player player = (Player) sender;
        boolean premium = false;
        try {
            premium = MembershipUtil.isExecutive(MembershipUtil.findMembership(player.getUniqueId()));
        } catch(SQLException ex) {
            sender.sendMessage("§cThere was an internal error when searching the membership system. Please try again later.");
            return false;
        }
        if(!premium) {
            sender.sendMessage("§cYou need a premium level membership in order to run this command! Run /membership to upgrade!");
            return false;
        }
        if(args.length == 0 || args == null) {
            sender.sendMessage("§cUsage: /skin <Name> - Change your current skin to another player skin");
            return true;
        }
        String name = args[0];
        if(name.equalsIgnoreCase("reset")) {
            // Reset your skin
            player.sendMessage("§8Processing...");
            NickAPI.resetNick( player );
            NickAPI.resetSkin( player );
            NickAPI.resetUniqueId( player );
            NickAPI.resetGameProfileName( player );
            NickAPI.refreshPlayer( player );
            player.sendMessage("§7You have reset your skin!");
            return true;
        }

        player.sendMessage("§8Processing...");
        NickAPI.nick(player, name );
        NickAPI.setSkin(player, name );
        NickAPI.setUniqueId( player, name );
        NickAPI.setGameProfileName( player, name );
        NickAPI.refreshPlayer( player );
        player.sendMessage("§7You now appear to others as §6" + name + "§7!");
        return true;
    }
}
