package me.bermine.sdm.listeners;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.events.DuelEndEvent;
import me.bermine.sdm.StrikeDeathMessages;
import me.bermine.sdm.util.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class StrikeListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        FileConfiguration config = StrikeDeathMessages.getInstance().getConfig();
        if (!config.getBoolean("disconnect_message.enabled")) return;
        StrikePracticeAPI api = StrikePractice.getAPI();
        Player player = e.getPlayer();
        if (api.getFight(player) != null) {
            api.getFight(player).getPlayersInFight().forEach(p ->
                    p.sendMessage(Color.translate(config.getString("disconnect_message.format")
                        .replace("<player>", player.getName()))
            ));
        }
    }

    @EventHandler
    public void onDuelEnd(DuelEndEvent e) {
        FileConfiguration config = StrikeDeathMessages.getInstance().getConfig();
        if (!config.getBoolean("death_messages.enabled")) return;
        String winner = e.getWinner().getName();
        String looser = e.getLoser().getName();
        e.getFight().getPlayersInFight().forEach(p ->
            p.sendMessage(Color.translate(config.getString("death_messages.format"))
                .replace("<looser>", looser)
                .replace("<winner>", winner)
            )
        );
    }
}
