package me.bermine.sdm.util;

import lombok.experimental.UtilityClass;
import me.bermine.sdm.StrikeDeathMessages;
import org.bukkit.configuration.file.FileConfiguration;


/**
 * @author Bermine
 */
@UtilityClass
public class ConfigUtils {
    private final StrikeDeathMessages plugin = StrikeDeathMessages.getInstance();
    public FileConfiguration config() {
        return plugin.getConfig();
    }
}
