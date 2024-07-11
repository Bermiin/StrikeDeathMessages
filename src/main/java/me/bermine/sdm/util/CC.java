package me.bermine.sdm.util;

import me.bermine.titleapi.util.VersionUtil;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Bermine
 */
public class CC {

    private static final boolean SUPPORTS_HEXCOLORS = VersionUtil.isOrOver(15);
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F\\d]{6}");

    private CC() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String message) {
        if (SUPPORTS_HEXCOLORS) {
            Matcher matcher = HEX_PATTERN.matcher(message);
            while (matcher.find()) {
                final String hexCode = message.substring(matcher.start(), matcher.end());
                final String replaceSharp = hexCode.replace('#', 'x');
                final char[] ch = replaceSharp.toCharArray();
                final StringBuilder builder = new StringBuilder();
                for (final char c : ch) {
                    builder.append("&").append(c);
                }
                message = message.replace(hexCode, builder.toString());
                matcher = HEX_PATTERN.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> messages) {
        return messages.stream().map(CC::translate).collect(Collectors.toList());
    }

}
