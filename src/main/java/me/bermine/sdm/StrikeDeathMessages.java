package me.bermine.sdm;

import lombok.Getter;
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
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }
        this.getServer().getPluginManager().registerEvents(new StrikeListeners(), this);
        getLogger().info("Plugin has been enabled");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("Plugin has been disabled");
    }
}
