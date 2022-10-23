package me.bermine.sdm.util;

import lombok.experimental.UtilityClass;
import me.bermine.sdm.StrikeDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

@UtilityClass
public class ReflectionUtils {

    private final StrikeDeathMessages plugin = StrikeDeathMessages.getInstance();
    private static final String NMS_PACKAGE;
    private static final String CRAFT_BUKKIT_PACKAGE;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        NMS_PACKAGE = "net.minecraft.server." + version + ".";
        CRAFT_BUKKIT_PACKAGE = "org.bukkit.craftbukkit." + version + ".";
    }

    // Credit to https://github.com/ConnorLinfoot/ for this reflection
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        try {
            Object times;
            Object chatTitle;
            Object chatSubtitle;
            Constructor<?> subtitleConstructor;
            Object titlePacket;
            Object subtitlePacket;

            if (title != null) {
                times = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                titlePacket = subtitleConstructor.newInstance(times, chatTitle, fadeIn, stay, fadeOut);
                sendPacket(player, titlePacket);

                times = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
                titlePacket = subtitleConstructor.newInstance(times, chatTitle);
                sendPacket(player, titlePacket);
            }

            if (subtitle != null) {
                times = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(times, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(player, subtitlePacket);

                times = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(times, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(player, subtitlePacket);
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Unexpected exception while sending title to player", e);
        }
    }

    public Class<?> getNMSClass(String clazz) {
        try {
            return Class.forName(NMS_PACKAGE + clazz);
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not find NMS class " + NMS_PACKAGE + clazz, e);
            return null;
        }
    }

    public Class<?> getCBClass(String clazz) {
        try {
            return Class.forName(CRAFT_BUKKIT_PACKAGE + clazz);
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not find CraftBukkit class " + CRAFT_BUKKIT_PACKAGE + clazz, e);
            return null;
        }
    }

    private void sendPacket(Player player, Object packet) {
        try {
            Object getHandle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = getHandle.getClass().getDeclaredField("playerConnection");
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Unexpected exception while sending packet to player", e);
        }

    }
}
