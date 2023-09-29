package me.bermine.sdm.tasks;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.fights.Fight;
import me.bermine.sdm.Config;
import me.bermine.sdm.StrikeDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Bermine
 */
public class VoidTask extends BukkitRunnable {

    private final StrikeDeathMessages plugin;
    public VoidTask(StrikeDeathMessages plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Config.DEATH_ENABLED.asBoolean()) return;
            AtomicBoolean teleporting = new AtomicBoolean();
            if (player.getLocation().getBlockY() < 0 && !teleporting.get()) {
                teleporting.set(true);
                StrikePracticeAPI api = StrikePractice.getAPI();
                Fight fight = api.getFight(player);
                if (fight == null) continue;

                Player opponent = fight.getPlayersInFight().stream()
                        .filter(p -> p.getUniqueId().equals(player.getUniqueId()))
                        .findFirst()
                        .orElse(null);
                if (opponent == null) continue;

                String message = Config.DEATH_MESSAGE_NO_PLAYER.asString()
                        .replace("<player>", player.getName())
                        .replace("<opponent>", opponent.getName());
                fight.getPlayersInFight().forEach(fightPlayer -> fightPlayer.sendMessage(message));
                fight.getSpectators().forEach(spectator -> spectator.sendMessage(message));
                Bukkit.getScheduler().runTaskLater(plugin, () -> teleporting.set(false), 5L);
            }
        }
    }
}
