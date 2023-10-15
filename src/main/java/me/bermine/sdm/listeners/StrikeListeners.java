package me.bermine.sdm.listeners;

import com.connorlinfoot.titleapi.TitleAPI;
import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
import ga.strikepractice.fights.Fight;
import lombok.RequiredArgsConstructor;
import me.bermine.sdm.Config;
import me.bermine.sdm.StrikeDeathMessages;
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
        if (!Config.DEATH_ENABLED.asBoolean()) return;
        if (event.getWinner() == null || event.getLoser() == null) return;
        String winner = event.getWinner().getName();
        String loser = event.getLoser().getName();
        if (Config.END_SOUND_ENABLED.asBoolean()) {
            String sound = Config.END_SOUND_VALUE.asString();
            float v = (float) Config.END_SOUND_VOLUME.asDouble();
            float p = (float) Config.END_SOUND_PITCH.asDouble();
            event.getLoser().playSound(event.getLoser().getLocation(), Sound.valueOf(sound), v, p);
            event.getWinner().playSound(event.getLoser().getLocation(), Sound.valueOf(sound), v, p);
        }
        Fight fight = event.getFight();
        if(fight == null) {
            return;
        }

        if (Config.WIN_TITLE_ENABLED.asBoolean()) {
            String title = Config.WIN_TITLE_TITLE.asString()
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            String subTitle = Config.WIN_TITLE_SUBTITLE.asString()
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            int fadeIn = Config.WIN_TITLE_FADEIN.asInt();
            int stay = Config.WIN_TITLE_STAY.asInt();
            int fadeOut = Config.WIN_TITLE_FADEOUT.asInt();
            TitleAPI.sendTitle(event.getWinner(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (Config.LOSE_TITLE_ENABLED.asBoolean()) {
            String title = Config.LOSE_TITLE_TITLE.asString()
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            String subTitle = Config.LOSE_TITLE_SUBTITLE.asString()
                    .replace("<loser>", event.getLoser().getName())
                    .replace("<winner>", event.getWinner().getName());
            int fadeIn = Config.LOSE_TITLE_FADEIN.asInt();
            int stay = Config.LOSE_TITLE_STAY.asInt();
            int fadeOut = Config.LOSE_TITLE_FADEOUT.asInt();
            TitleAPI.sendTitle(event.getLoser(), title, subTitle, fadeIn, stay, fadeOut);
        }

        if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
            if (fight.getKit().isBridges() || fight.getKit().isBedwars()) {
                String message = Config.DEATH_MESSAGE_WIN.asString()
                        .replace("<looser>", loser)
                        .replace("<winner>", winner);
                fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
                fight.getSpectators().forEach(player -> player.sendMessage(message));
                return;
            }

            String message = Config.DEATH_MESSAGE.asString()
                    .replace("<looser>", loser)
                    .replace("<winner>", winner);
            fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
            fight.getSpectators().forEach(player -> player.sendMessage(message));
        }
    }

    @EventHandler
    public void onDuelStart(DuelStartEvent e) {
        if (!Config.START_ENABLED.asBoolean()) return;
        if (Config.START_TITLE_ENABLED.asBoolean()) {
            String title = Config.START_TITLE_TITLE.asString();
            String subTitle = Config.START_TITLE_SUBTITLE.asString();
            int fadeIn = Config.START_TITLE_FADEIN.asInt();
            int stay = Config.START_TITLE_STAY.asInt();
            int fadeOut = Config.START_TITLE_FADEOUT.asInt();
            e.getFight().getPlayersInFight().forEach(p -> TitleAPI.sendTitle(p, title, subTitle, fadeIn, stay, fadeOut));
        }
        if (!Config.START_DISABLE_MESSAGE.asBoolean()) {
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                Config.START_MESSAGE.asList().forEach(s -> {
                    e.getPlayer1().sendMessage(s);
                    e.getPlayer2().sendMessage(s);
                }),101L);
        }
    }
}
