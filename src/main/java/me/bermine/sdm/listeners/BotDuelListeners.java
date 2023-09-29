package me.bermine.sdm.listeners;

import ga.strikepractice.events.BotDuelEndEvent;
import me.bermine.sdm.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BotDuelListeners implements Listener {

    @EventHandler
    public void handleBotDuels(BotDuelEndEvent e) {
        String message = Config.DEATH_MESSAGE.asString()
                .replace("<winner>", e.getWinner())
                .replace("<looser>", e.getLoser());
        e.getFight().getPlayersInFight().forEach(player -> player.sendMessage(message));
        e.getFight().getSpectators().forEach(player -> player.sendMessage(message));
    }
}
