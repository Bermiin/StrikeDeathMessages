package me.bermine.sdm;

import lombok.RequiredArgsConstructor;
import me.bermine.sdm.util.CC;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public enum Config {
    NO_PERMISSIONS("no_permissions", "&cNo permission."),
    DEATH_ENABLED("death.enabled", true),
    DEATH_DISABLE_MESSAGE("death.disable_message", false),
    DEATH_MESSAGE("death.message", "&a<winner> &7Killed &c<looser>"),
    DEATH_MESSAGE_NO_PLAYER("death.message_no_player", "&c<player> &7died."),
    DEATH_MESSAGE_WIN("death.message_win", "&a<player> &7won the game."),
    SOUND_ENABLED("death.sound.enabled", true),
    SOUND_VALUE("death.sound.sound", "AMBIENCE_THUNDER"),
    SOUND_VOLUME("death.sound.volume", 1.0),
    SOUND_PITCH("death.sound.pitch", 1.0),
    WIN_TITLE_ENABLED("death.win_title.enabled", true),
    WIN_TITLE_TITLE("death.win_title.title", "&a&lVICTORY"),
    WIN_TITLE_SUBTITLE("death.win_title.subtitle", "&a<winner> &fhas won the fight"),
    WIN_TITLE_FADEIN("death.win_title.fadeIn", 10),
    WIN_TITLE_STAY("death.win_title.stay", 20),
    WIN_TITLE_FADEOUT("death.win_title.fadeOut", 10),
    LOSE_TITLE_ENABLED("death.loose_title.enabled", true),
    LOSE_TITLE_TITLE("death.loose_title.title", "&c&lDEFEAT"),
    LOSE_TITLE_SUBTITLE("death.loose_title.subtitle", "&a<winner> &fhas won the fight"),
    LOSE_TITLE_FADEIN("death.loose_title.fadeIn", 10),
    LOSE_TITLE_STAY("death.loose_title.stay", 20),
    LOSE_TITLE_FADEOUT("death.loose_title.fadeOut", 10),
    START_ENABLED("start.enabled", true),
    START_DISABLE_MESSAGE("start.disable_message", false),
    START_MESSAGE("start.message", Arrays.asList(
            " ",
            "&4&l[WARNING] &c&lDrag clicking or butterfly clicking might result in a ban!",
            " "
    )),
    START_TITLE_ENABLED("start.title.enabled", true),
    START_TITLE_TITLE("start.title.title", "&c&lFight"),
    START_TITLE_SUBTITLE("start.title.subtitle", "Match has started"),
    START_TITLE_FADEIN("start.title.fadeIn", 20),
    START_TITLE_STAY("start.title.stay", 20),
    START_TITLE_FADEOUT("start.title.fadeOut", 20)
    ;

private final String path;
private final Object def;
private static StrikeDeathMessages plugin;

    public String asString() {
        return CC.translate(plugin.getConfig().getString(path, (String) def));
    }
    public boolean asBoolean() {
        return plugin.getConfig().getBoolean(path, (boolean) def);
    }
    public int asInt() {
        return plugin.getConfig().getInt(path, (int) def);
    }
    public double asDouble() {
        return plugin.getConfig().getDouble(path, (double) def);
    }
    public List<String> asList() {
        return CC.translate(plugin.getConfig().getStringList(path));
    }

    public static void init(StrikeDeathMessages plugin) {
        Config.plugin = plugin;
    }
}
