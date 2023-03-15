package me.bermine.sdm.listeners;

import ga.strikepractice.events.BotDuelEndEvent;
import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
import ga.strikepractice.fights.Fight;
import lombok.RequiredArgsConstructor;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Bermine
 */
@RequiredArgsConstructor
public class StrikeListeners implements Listener {

    private final StrikeDeathMessages plugin;

    @EventHandler
    public void onDuelEnd(DuelEndEvent event) {
        if (!plugin.getConfig().getBoolean("death.enabled")) return;
        if (event.getWinner() == null || event.getLoser() == null) return;
        String winner = event.getWinner().getName();
        String loser = event.getLoser().getName();
        if (plugin.getConfig().getBoolean("death.sound.enabled")) {
            String sound = plugin.getConfig().getString("death.sound.sound");
            float v = plugin.getConfig().getInt("death.sound.volume");
            float p = plugin.getConfig().getInt("death.sound.pitch");
            event.getLoser().playSound(event.getLoser().getLocation(), Sound.valueOf(sound), v, p);
            event.getWinner().playSound(event.getLoser().getLocation(), Sound.valueOf(sound), v, p);
        }
        Fight fight = event.getFight();
        if(fight == null) {
            return;
        }

        /*if (ConfigUtils.WINNER_TITLE_ENABLED) {
            String title = ConfigUtils.WINNER_TITLE
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            String subTitle = ConfigUtils.WINNER_SUBTITLE
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            int fadeIn = ConfigUtils.WINNER_TITLE_FI;
            int stay = ConfigUtils.WINNER_TITLE_S;
            int fadeOut = ConfigUtils.WINNER_TITLE_FO;
            Reflections.sendTitle(event.getWinner(), title, subTitle, fadeIn, stay, fadeOut);
        }*/

        /*if (ConfigUtils.LOSER_TITLE_ENABLED) {
            String title = ConfigUtils.LOSER_TITLE
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            String subTitle = ConfigUtils.LOSER_SUBTITLE
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            int fadeIn = ConfigUtils.LOSER_TITLE_FI;
            int stay = ConfigUtils.LOSER_TITLE_S;
            int fadeOut = ConfigUtils.LOSER_TITLE_FO;
            Reflections.sendTitle(event.getLoser(), title, subTitle, fadeIn, stay, fadeOut);
        }*/

        if (fight.getKit().isBridges() || fight.getKit().isBedwars()) {
            String message = CC.translate(plugin.getConfig().getString("death.message_win"))
                    .replace("<looser>", loser)
                    .replace("<winner>", winner);
            fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
            fight.getSpectators().forEach(player -> player.sendMessage(message));
            return;
        }

        String message = CC.translate(plugin.getConfig().getString("death.message"))
                .replace("<looser>", loser)
                .replace("<winner>", winner);
        fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
        fight.getSpectators().forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void handleBotDuels(BotDuelEndEvent e) {
        if (!plugin.getConfig().getBoolean("death.enabled")) return;
        String message = CC.translate(plugin.getConfig().getString("death.message"))
                .replace("<winner>", e.getWinner())
                .replace("<looser>", e.getLoser());
        e.getFight().getPlayersInFight().forEach(player -> player.sendMessage(message));
        e.getFight().getSpectators().forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void onDuelStart(DuelStartEvent e) {
        if (!plugin.getConfig().getBoolean("start.enabled")) return;
        /*if (ConfigUtils.START_TITLE_ENABLED) {
            String title = ConfigUtils.START_TITLE;
            String subTitle = ConfigUtils.START_SUBTITLE;
            int fadeIn = ConfigUtils.START_TITLE_FI;
            int stay = ConfigUtils.START_TITLE_S;
            int fadeOut = ConfigUtils.START_TITLE_FO;
            e.getFight().getPlayersInFight().forEach(p -> Reflections.sendTitle(p, title, subTitle, fadeIn, stay, fadeOut));
        }*/
        Bukkit.getScheduler().runTaskLater(plugin, () ->
            CC.translate(plugin.getConfig().getStringList("start.message")).forEach(s -> {
                e.getPlayer1().sendMessage(s);
                e.getPlayer2().sendMessage(s);
            }),101L);
    }
}
