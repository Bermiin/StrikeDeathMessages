package me.bermine.sdm.commands;

import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.CC;
import me.bermine.sdm.util.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Bermine
 */
public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("sdm.reload")) {
            sender.sendMessage(CC.translate(ConfigUtils.config().getString("no_perms")));
            return true;
        }
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(CC.RED + "Usage: /sdm reload");
            return true;
        }
        StrikeDeathMessages.getInstance().reloadConfig();
        sender.sendMessage(CC.GREEN + "Successfully reloaded config.yml");
        return false;
    }
}
