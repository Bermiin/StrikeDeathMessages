package me.bermine.sdm.util;

import lombok.experimental.UtilityClass;
import me.bermine.sdm.StrikeDeathMessages;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Bermine
 */
@UtilityClass
public class ConfigUtils {
    // TODO: Refactor this goofy ass code
    private final StrikeDeathMessages plugin = StrikeDeathMessages.getInstance();
    public final String NO_PERMS = CC.translate(getConfig().getString("no_perms"));
    public final boolean DEATH_ENABLED = getConfig().getBoolean("death.enabled");
    public final String DEATH_MSG = CC.translate(getConfig().getString("death.message"));
    public final String DEATH_MSG_NO_PL = CC.translate(getConfig().getString("death.message_no_player"));
    public final String DEATH_WIN = CC.translate(getConfig().getString("death.message_win"));
    public final boolean DEATH_SOUND_ENABLED = getConfig().getBoolean("death.sound.enabled");
    public final String DEATH_SOUND = CC.translate(getConfig().getString("death.sound.sound"));
    public final float DEATH_SOUND_VOLUME = (float) getConfig().getInt("death.sound.volume");
    public final float DEATH_SOUND_PITCH = (float) getConfig().getInt("death.sound.pitch");
    public final boolean START_ENABLED = getConfig().getBoolean("start.enabled");
    public final List<String> START_MSG = getConfig().getStringList("start.message").stream()
            .map(CC::translate)
            .collect(Collectors.toList());

    public final boolean START_TITLE_ENABLED = getConfig().getBoolean("start.title.enabled");
    public final String START_TITLE = CC.translate(getConfig().getString("start.title.title"));
    public final String START_SUBTITLE = CC.translate(getConfig().getString("start.title.subtitle"));
    public final int START_TITLE_FI = getConfig().getInt("start.title.fadeIn");
    public final int START_TITLE_S = getConfig().getInt("start.title.stay");
    public final int START_TITLE_FO = getConfig().getInt("start.title.fadeOut");

    public final boolean WINNER_TITLE_ENABLED = getConfig().getBoolean("death.win_title.enabled");
    public final String WINNER_TITLE = CC.translate(getConfig().getString("death.win_title.title"));
    public final String WINNER_SUBTITLE = CC.translate(getConfig().getString("death.win_title.subtitle"));
    public final int WINNER_TITLE_FI = getConfig().getInt("death.win_title.fadeIn");
    public final int WINNER_TITLE_S = getConfig().getInt("death.win_title.stay");
    public final int WINNER_TITLE_FO = getConfig().getInt("death.win_title.fadeOut");
    public final boolean LOSER_TITLE_ENABLED = getConfig().getBoolean("death.lose_title.enabled");
    public final String LOSER_TITLE = CC.translate(getConfig().getString("death.lose_title.title"));
    public final String LOSER_SUBTITLE = CC.translate(getConfig().getString("death.lose_title.subtitle"));
    public final int LOSER_TITLE_FI = getConfig().getInt("death.lose_title.fadeIn");
    public final int LOSER_TITLE_S = getConfig().getInt("death.lose_title.stay");
    public final int LOSER_TITLE_FO = getConfig().getInt("death.lose_title.fadeOut");

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
    public FileConfiguration config() {
        return plugin.getConfig();
    }
}
