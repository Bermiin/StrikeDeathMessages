package me.bermine.sdm.listeners;

import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
import ga.strikepractice.fights.Fight;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.CC;
import me.bermine.sdm.util.ConfigUtils;
import me.bermine.sdm.util.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Bermine
 */
public class StrikeListeners implements Listener {

    @EventHandler
    public void onDuelEnd(DuelEndEvent event) {
        FileConfiguration config = ConfigUtils.config();
        if (!config.getBoolean("death.enabled")) return;
        if (event.getWinner() == null || event.getLoser() == null) return;
        String winner = event.getWinner().getName();
        String loser = event.getLoser().getName();
        if (config.getBoolean("death.sound.enabled")) {
            Sound sound = Sound.valueOf(config.getString("death.sound.sound"));
            float volume = (float) config.getInt("death.sound.volume");
            float pitch = (float) config.getInt("death.sound.pitch");
            event.getLoser().playSound(event.getLoser().getLocation(), sound, volume, pitch);
            event.getWinner().playSound(event.getLoser().getLocation(), sound, volume, pitch);
        }
        Fight fight = event.getFight();
        if(fight == null) {
            return;
        }

        if (config.getBoolean("death.win_title.enabled")) {
            String title = CC.translate(config.getString("death.win_title.title"))
                .replace("<loser>", loser)
                .replace("<winner>", winner);
            String subTitle = CC.translate(config.getString("death.win_title.subtitle"))
                .replace("<loser>", loser)
                .replace("<winner>", winner);
            int fadeIn = config.getInt("death.win_title.fadeIn");
            int stay = config.getInt("death.win_title.stay");
            int fadeOut = config.getInt("death.win_title.fadeOut");
            Reflections.sendTitle(event.getWinner(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (config.getBoolean("death.lose_title.enabled")) {
            String title = CC.translate(config.getString("death.lose_title.title"))
                .replace("<loser>", loser)
                .replace("<winner>", winner);
            String subTitle = CC.translate(config.getString("death.lose_title.subtitle"))
                .replace("<loser>", loser)
                .replace("<winner>", winner);
            int fadeIn = config.getInt("death.lose_title.fadeIn");
            int stay = config.getInt("death.lose_title.stay");
            int fadeOut = config.getInt("death.lose_title.fadeOut");
            Reflections.sendTitle(event.getLoser(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (fight.getKit().isBridges() || fight.getKit().isBedwars()) {
            String message = CC.translate(config.getString("death.message_win"))
                .replace("<looser>", loser)
                .replace("<winner>", winner);
            fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
            fight.getSpectators().forEach(player -> player.sendMessage(message));
            return;
        }

        String message = CC.translate(config.getString("death.message"))
            .replace("<looser>", loser)
            .replace("<winner>", winner);
        fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
        fight.getSpectators().forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void onDuelStart(DuelStartEvent e) {
        FileConfiguration config = ConfigUtils.config();
        if (!config.getBoolean("start.enabled")) return;
        if (config.getBoolean("start.title.enabled")) {
            String title = CC.translate(config.getString("start.title.title"));
            String subTitle = CC.translate(config.getString("start.title.subtitle"));
            int fadeIn = config.getInt("start.title.fadeIn");
            int stay = config.getInt("start.title.stay");
            int fadeOut = config.getInt("start.title.fadeOut");
            e.getFight().getPlayersInFight().forEach(p -> Reflections.sendTitle(p, title, subTitle, fadeIn, stay, fadeOut));
        }
        Bukkit.getScheduler().runTaskLater(StrikeDeathMessages.getInstance(), () ->
            ConfigUtils.config().getStringList("start.message")
                .stream()
                .map(CC::translate)
                .forEach(s -> {
                    e.getPlayer1().sendMessage(s);
                    e.getPlayer2().sendMessage(s);
            }),101L);
    }
}
