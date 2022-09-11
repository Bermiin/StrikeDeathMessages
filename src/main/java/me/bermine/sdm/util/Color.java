package me.bermine.sdm.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

/**
 * @author Bermine
 */
@UtilityClass
public class Color {

    public String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
