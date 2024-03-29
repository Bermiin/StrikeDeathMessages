package me.bermine.sdm.tasks;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.fights.Fight;
import me.bermine.sdm.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Bermine
 */
public class VoidTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Config.DEATH_ENABLED.asBoolean()) return;
            if (player.getLocation().getBlockY() < 0) {
                StrikePracticeAPI api = StrikePractice.getAPI();
                Fight fight = api.getFight(player);
                if (fight == null || fight.hasEnded()) continue;

                String message = Config.DEATH_MESSAGE_NO_PLAYER.asString()
                        .replace("<player>", player.getName());
                fight.getPlayersInFight().forEach(fightPlayer -> fightPlayer.sendMessage(message));
                fight.getSpectators().forEach(spectator -> spectator.sendMessage(message));
            }
        }
    }
}
