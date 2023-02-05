package me.bermine.sdm;

import lombok.Getter;
import me.bermine.sdm.commands.MainCommand;
import me.bermine.sdm.listeners.PlayerListeners;
import me.bermine.sdm.listeners.StrikeListeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Bermine
 */
public final class StrikeDeathMessages extends JavaPlugin {

    @Getter private static StrikeDeathMessages instance;

    @Override
    public void onEnable() {
        if (!this.getDescription().getAuthors().contains("Bermine") || !this.getDescription().getName().equalsIgnoreCase("StrikeDeathMessages")) {
            getLogger().warning("You edited the plugin.yml");
            getLogger().warning("Haha get caught in 4k");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        instance = this;
        createConfig();
        this.getServer().getPluginManager().registerEvents(new StrikeListeners(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        this.getCommand("sdm reload").setExecutor(new MainCommand());
        getLogger().info("Plugin has been enabled");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("Plugin has been disabled");
    }

    private void createConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }
}
