package me.bermine.sdm.commands;

import lombok.RequiredArgsConstructor;
import me.bermine.sdm.Config;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Bermine
 */
@RequiredArgsConstructor
public class MainCommand implements CommandExecutor {

    private final StrikeDeathMessages plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("sdm.reload")) {
            sender.sendMessage(Config.NO_PERMISSIONS.asString());
            return true;
        }
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(CC.translate("&cUsage: /sdm reload"));
            return true;
        }
        plugin.reloadConfig();
        Config.validateSound();
        sender.sendMessage(ChatColor.GREEN + "Reloaded config.yml. Check console for any errors.");
        return false;
    }
}
