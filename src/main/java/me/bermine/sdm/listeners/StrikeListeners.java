package me.bermine.sdm.listeners;

import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
import ga.strikepractice.fights.Fight;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.ConfigUtils;
import me.bermine.sdm.util.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Bermine
 */
public class StrikeListeners implements Listener {

    @EventHandler
    public void onDuelEnd(DuelEndEvent event) {
        if (!ConfigUtils.DEATH_ENABLED) return;
        if (event.getWinner() == null || event.getLoser() == null) return;
        String winner = event.getWinner().getName();
        String loser = event.getLoser().getName();
        if (ConfigUtils.DEATH_SOUND_ENABLED) {
            event.getLoser().playSound(event.getLoser().getLocation(), Sound.valueOf(ConfigUtils.DEATH_SOUND), ConfigUtils.DEATH_SOUND_VOLUME, ConfigUtils.DEATH_SOUND_PITCH);
            event.getWinner().playSound(event.getLoser().getLocation(), Sound.valueOf(ConfigUtils.DEATH_SOUND), ConfigUtils.DEATH_SOUND_VOLUME, ConfigUtils.DEATH_SOUND_PITCH);
        }
        Fight fight = event.getFight();
        if(fight == null) {
            return;
        }

        if (ConfigUtils.WINNER_TITLE_ENABLED) {
            String title = ConfigUtils.WINNER_TITLE
                .replace("<loser>", event.getLoser().getName())
                .replace("<winner>", event.getWinner().getName());
            String subTitle = ConfigUtils.WINNER_SUBTITLE
                .replace("<loser>", event.getLoser().getName())
                .replace("<winner>", event.getWinner().getName());
            int fadeIn = ConfigUtils.WINNER_TITLE_FI;
            int stay = ConfigUtils.WINNER_TITLE_S;
            int fadeOut = ConfigUtils.WINNER_TITLE_FO;
            ReflectionUtils.sendTitle(event.getWinner(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (ConfigUtils.LOSER_TITLE_ENABLED) {
            String title = ConfigUtils.LOSER_TITLE
                .replace("<loser>", event.getLoser().getName())
                .replace("<winner>", event.getWinner().getName());
            String subTitle = ConfigUtils.LOSER_SUBTITLE
                .replace("<loser>", event.getLoser().getName())
                .replace("<winner>", event.getWinner().getName());
            int fadeIn = ConfigUtils.LOSER_TITLE_FI;
            int stay = ConfigUtils.LOSER_TITLE_S;
            int fadeOut = ConfigUtils.LOSER_TITLE_FO;
            ReflectionUtils.sendTitle(event.getLoser(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (fight.getKit().isBridges() || fight.getKit().isBedwars()) {
            String message = ConfigUtils.DEATH_WIN
                .replace("<looser>", loser)
                .replace("<winner>", winner);
            fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
            fight.getSpectators().forEach(player -> player.sendMessage(message));
            return;
        }

        String message = ConfigUtils.DEATH_MSG
            .replace("<looser>", loser)
            .replace("<winner>", winner);
        fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
        fight.getSpectators().forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void onDuelStart(DuelStartEvent e) {
        if (!ConfigUtils.START_ENABLED) return;
        if (ConfigUtils.START_TITLE_ENABLED) {
            String title = ConfigUtils.START_TITLE;
            String subTitle = ConfigUtils.START_SUBTITLE;
            int fadeIn = ConfigUtils.START_TITLE_FI;
            int stay = ConfigUtils.START_TITLE_S;
            int fadeOut = ConfigUtils.START_TITLE_FO;
            e.getFight().getPlayersInFight().forEach(p -> ReflectionUtils.sendTitle(p, title, subTitle, fadeIn, stay, fadeOut));
        }
        Bukkit.getScheduler().runTaskLater(StrikeDeathMessages.getInstance(), () ->
            ConfigUtils.START_MSG.forEach(s -> {
                e.getPlayer1().sendMessage(s);
                e.getPlayer2().sendMessage(s);
        }),101L);
    }
}
