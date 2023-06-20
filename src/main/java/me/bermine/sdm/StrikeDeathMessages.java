package me.bermine.sdm;

import com.connorlinfoot.titleapi.TitleAPI;
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
        if (!this.getDescription().getAuthors().contains("Bermine") || !this.getDescription().getName().equals("StrikeDeathMessages")) {
            getLogger().warning("You edited the plugin.yml, haha get caught in 4k");
            getLogger().warning("Please check your plugin.yml and try again");
            getLogger().warning("Disabling plugin...");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        instance = this;
        createConfig();
        Config.init(this);
        new TitleAPI();
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
