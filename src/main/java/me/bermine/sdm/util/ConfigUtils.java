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

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
