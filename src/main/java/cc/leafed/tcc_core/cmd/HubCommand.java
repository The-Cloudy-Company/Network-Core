package cc.leafed.tcc_core.cmd;

import cc.leafed.tcc_core.Core;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou are not able to run this command.");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("§6Returning you to the hub...");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("Hub");// TODO: Don't hardcode the hub server incase it changes
        player.sendPluginMessage(Core.getCore(), "BungeeCord", out.toByteArray());
        return true;
    }
}
