package me.bermine.sdm.listeners;

import ga.strikepractice.events.DuelEndEvent;
import ga.strikepractice.events.DuelStartEvent;
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
    public void onDuelEnd(DuelEndEvent e) {
        FileConfiguration config = StrikeDeathMessages.getInstance().getConfig();
        if (!config.getBoolean("death.enabled")) return;
        String winner = e.getWinner().getName();
        String looser = e.getLoser().getName();
        if (config.getBoolean("death.sound.enabled")) {
            e.getLoser().playSound(e.getLoser().getLocation(), Sound.valueOf(config.getString("death.sound.sound")), (float) config.getInt("death.sound.volume"), (float) config.getInt("death.sound.pitch"));
            e.getWinner().playSound(e.getLoser().getLocation(), Sound.valueOf(config.getString("death.sound.sound")), (float) config.getInt("death.sound.volume"), (float) config.getInt("death.sound.pitch"));
        }
        e.getFight().getPlayersInFight().forEach(p ->
            p.sendMessage(Color.translate(config.getString("death.message")
                    .replace("<looser>", looser)
                    .replace("<winner>", winner)
            ))
        );
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
