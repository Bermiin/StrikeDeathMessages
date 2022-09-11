package me.bermine.sdm.listeners;

import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
import ga.strikepractice.fights.Fight;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.Color;
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
        FileConfiguration config = StrikeDeathMessages.getInstance().getConfig();
        if (!config.getBoolean("death.enabled")) return;
        String winner = event.getWinner().getName();
        String loser = event.getLoser().getName();
        if (config.getBoolean("death.sound.enabled")) {
            event.getLoser().playSound(event.getLoser().getLocation(), Sound.valueOf(config.getString("death.sound.sound")), (float) config.getInt("death.sound.volume"), (float) config.getInt("death.sound.pitch"));
            event.getWinner().playSound(event.getLoser().getLocation(), Sound.valueOf(config.getString("death.sound.sound")), (float) config.getInt("death.sound.volume"), (float) config.getInt("death.sound.pitch"));
        }
        Fight fight = event.getFight();
        if(fight == null) {
            return;
        }
        String message = Color.translate(config.getString("death.message")
                        .replace("<looser>", loser)
                        .replace("<winner>", winner));
        fight.getPlayersInFight().forEach(player -> player.sendMessage(message));
        fight.getSpectators().forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void onDuelStart(DuelStartEvent e) {
        FileConfiguration config = StrikeDeathMessages.getInstance().getConfig();
        if (!config.getBoolean("start.enabled")) return;
        Bukkit.getScheduler().runTaskLater(StrikeDeathMessages.getInstance(), () ->
            config.getStringList("start.message").stream().map(Color::translate).forEach(s -> {
                e.getPlayer1().sendMessage(s);
                e.getPlayer2().sendMessage(s);
            }),101L);
    }
}
