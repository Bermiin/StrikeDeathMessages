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
            getLogger().warning("y r u changing the plugin.yml ( ͡° ͜ʖ ͡°)");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        instance = this;
        createConfig();
        this.getServer().getPluginManager().registerEvents(new StrikeListeners(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        this.getCommand("sdm").setExecutor(new MainCommand(this));
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
