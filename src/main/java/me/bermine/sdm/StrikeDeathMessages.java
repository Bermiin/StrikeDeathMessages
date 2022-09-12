package me.bermine.sdm;

import lombok.Getter;
import me.bermine.sdm.commands.MainCommand;
import me.bermine.sdm.listeners.PlayerListeners;
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
                && getConfig().getConfigurationSection("start_message") != null
                || getConfig().getString("death.message_no_player") == null
                || getConfig().getString("death.message_win") == null
        ) updateConfig();
        this.getServer().getPluginManager().registerEvents(new StrikeListeners(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
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
        values.put("no-perm", c.getString("no_perms"));
        values.put("dm-enabled", c.getBoolean("death.enabled"));
        values.put("dm-format", c.getString("death.format"));
        values.put("sm-enabled", c.getBoolean("start.enabled"));
        values.put("sm-format", c.getStringList("start.format"));
        values.put("snd-enabled", c.getBoolean("death.sound.enabled"));
        values.put("snd-snd", c.getString("death.sound.sound"));
        values.put("snd-vlm", c.getBoolean("death.sound.volume"));
        values.put("snd-pitch", c.getBoolean("death.sound.pitch"));
        File oldFile = new File(this.getDataFolder(), "config.yml");
        if (oldFile.exists()) oldFile.delete();
        createConfig();
        c.set("no_perms", values.get("no-perm"));
        c.set("death.enabled", values.get("dm-enabled"));
        c.set("death.message", values.get("dm-format"));
        c.set("sound.enabled", values.get("snd-enabled"));
        c.set("sound.sound", values.get("snd-snd"));
        c.set("sound.volume", values.get("snd-volume"));
        c.set("sound.pitch", values.get("snd-pitch"));
        c.set("start.enabled", values.get("sm-enabled"));
        c.set("start.message", values.get("sm-format"));
        reloadConfig();
        values.clear();
    }
}
