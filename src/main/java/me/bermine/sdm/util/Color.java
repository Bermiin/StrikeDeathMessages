package me.bermine.sdm.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

/**
 * @author Bermine
 */
@UtilityClass
public class Color {

    public static final String BLACK = ChatColor.BLACK.toString(); // 0
    public static final String D_BLUE = ChatColor.DARK_BLUE.toString(); // 1
    public static final String D_GREEN = ChatColor.DARK_GREEN.toString(); // 2
    public static final String D_AQUA = ChatColor.DARK_AQUA.toString(); // 3
    public static final String D_RED = ChatColor.DARK_RED.toString(); // 4
    public static final String D_PURPLE = ChatColor.DARK_PURPLE.toString(); // 5
    public static final String GOLD = ChatColor.GOLD.toString(); // 6
    public static final String GRAY = ChatColor.GRAY.toString(); // 7
    public static final String D_GRAY = ChatColor.DARK_GRAY.toString(); // 8
    public static final String BLUE = ChatColor.BLUE.toString(); // 9
    public static final String GREEN = ChatColor.GREEN.toString(); // a
    public static final String AQUA = ChatColor.AQUA.toString(); // b
    public static final String RED = ChatColor.RED.toString(); // c
    public static final String PURPLE = ChatColor.LIGHT_PURPLE.toString(); // d
    public static final String YELLOW = ChatColor.YELLOW.toString(); // e
    public static final String WHITE = ChatColor.WHITE.toString(); // f
    public static final String B = ChatColor.BOLD.toString(); // l
    public static final String I = ChatColor.ITALIC.toString(); // o
    public static final String S = ChatColor.STRIKETHROUGH.toString(); // m
    public static final String U = ChatColor.UNDERLINE.toString(); // n
    public static final String O = ChatColor.MAGIC.toString(); // k
    public static final String R = ChatColor.RESET.toString(); // r

    public String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
