package me.bermine.sdm;

import lombok.Getter;
import me.bermine.sdm.commands.MainCommand;
import me.bermine.sdm.listeners.StrikeListeners;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
        if (getConfig().getConfigurationSection("death_message") != null
                && getConfig().getConfigurationSection("start_message") != null) updateConfig();
        this.getServer().getPluginManager().registerEvents(new StrikeListeners(), this);
        this.getCommand("sdm").setExecutor(new MainCommand());
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

    private void updateConfig() {
        Map<String, Object> values = new HashMap<>();
        FileConfiguration c = this.getConfig();
        values.put("dm-enabled", c.getBoolean("death_message.enabled"));
        values.put("dm-format", c.getString("death_message.format"));
        values.put("sm-enabled", c.getBoolean("start_message.enabled"));
        values.put("sm-format", c.getStringList("start_message.format"));
        File oldFile = new File(this.getDataFolder(), "config.yml");
        if (oldFile.exists()) oldFile.delete();
        createConfig();
        c.set("death.enabled", values.get("dm-enabled"));
        c.set("death.message", values.get("dm-format"));
        c.set("start.enabled", values.get("sm-enabled"));
        c.set("start.message", values.get("sm-format"));
        reloadConfig();
        values.clear();
    }
}
