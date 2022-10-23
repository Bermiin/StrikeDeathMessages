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

    private final StrikeDeathMessages plugin = StrikeDeathMessages.getInstance();
    public final String NO_PERMS = Color.translate(getConfig().getString("no_perms"));
    public final boolean DEATH_ENABLED = getConfig().getBoolean("death.enabled");
    public final String DEATH_MSG = Color.translate(getConfig().getString("death.message"));
    public final String DEATH_MSG_NO_PL = Color.translate(getConfig().getString("death.message_no_player"));
    public final String DEATH_WIN = Color.translate(getConfig().getString("death.message_win"));
    public final boolean DEATH_SOUND_ENABLED = getConfig().getBoolean("death.sound.enabled");
    public final String DEATH_SOUND = Color.translate(getConfig().getString("death.sound.sound"));
    public final float DEATH_SOUND_VOLUME = (float) getConfig().getInt("death.sound.volume");
    public final float DEATH_SOUND_PITCH = (float) getConfig().getInt("death.sound.pitch");
    public final boolean START_ENABLED = getConfig().getBoolean("start.enabled");
    public final List<String> START_MSG = getConfig().getStringList("start.message").stream()
            .map(Color::translate)
            .collect(Collectors.toList());

    public final boolean START_TITLE_ENABLED = getConfig().getBoolean("start.title.enabled");
    public final String START_TITLE = Color.translate(getConfig().getString("start.title.title"));
    public final String START_SUBTITLE = Color.translate(getConfig().getString("start.title.subtitle"));
    public final int START_TITLE_FI = getConfig().getInt("start.title.fadeIn");
    public final int START_TITLE_S = getConfig().getInt("start.title.stay");
    public final int START_TITLE_FO = getConfig().getInt("start.title.fadeOut");

    public final boolean WINNER_TITLE_ENABLED = getConfig().getBoolean("death.win_title.enabled");
    public final String WINNER_TITLE = Color.translate(getConfig().getString("death.win_title.title"));
    public final String WINNER_SUBTITLE = Color.translate(getConfig().getString("death.win_title.subtitle"));
    public final int WINNER_TITLE_FI = getConfig().getInt("death.win_title.fadeIn");
    public final int WINNER_TITLE_S = getConfig().getInt("death.win_title.stay");
    public final int WINNER_TITLE_FO = getConfig().getInt("death.win_title.fadeOut");

    public final boolean LOSER_TITLE_ENABLED = getConfig().getBoolean("death.loose_title.enabled");
    public final String LOSER_TITLE = Color.translate(getConfig().getString("death.loose_title.title"));
    public final String LOSER_SUBTITLE = Color.translate(getConfig().getString("death.loose_title.subtitle"));
    public final int LOSER_TITLE_FI = getConfig().getInt("death.loose_title.fadeIn");
    public final int LOSER_TITLE_S = getConfig().getInt("death.loose_title.stay");
    public final int LOSER_TITLE_FO = getConfig().getInt("death.loose_title.fadeOut");

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
