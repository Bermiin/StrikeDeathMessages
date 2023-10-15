package me.bermine.sdm.listeners;

import com.cryptomorin.xseries.XSound;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.fights.BotFight;
import ga.strikepractice.fights.Fight;
import ga.strikepractice.fights.duel.BestOfFight;
import ga.strikepractice.fights.other.FFAFight;
import lombok.RequiredArgsConstructor;
import me.bermine.sdm.Config;
import me.bermine.sdm.StrikeDeathMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

/**
 * @author Bermine
 */
@RequiredArgsConstructor
public class PlayerListeners implements Listener {

    private final StrikeDeathMessages plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player) || !Config.DEATH_ENABLED.asBoolean()) return;
        StrikePracticeAPI api = StrikePractice.getAPI();
        Player damaged = (Player) e.getEntity();

        if (e.getDamage() < damaged.getHealth()) {
            return;
        }

        Fight fight = api.getFight(damaged);
        if (!(fight instanceof BestOfFight) || fight instanceof BotFight) return;

        List<Player> playersInFight = fight.getPlayersInFight();
        Player opponent = playersInFight.stream().filter(p -> !p.getName().equals(damaged.getName())).findFirst().orElse(null);
        if (opponent == null) return;

        if (fight.getKit().isBedwars() || fight.getKit().isBridges() && !fight.hasEnded()) {
            if (damaged.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
                playersInFight.forEach(player -> {
                    if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                        player.sendMessage(Config.DEATH_MESSAGE.asString()
                            .replace("<looser>", damaged.getName())
                            .replace("<winner>", opponent.getName()));
                    }
                    if (Config.DEATH_SOUND_ENABLED.asBoolean()) {
                        XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                        if (xSound == null || !xSound.isSupported()) return;
                        int volume = Config.DEATH_SOUND_VOLUME.asInt();
                        int pitch = Config.DEATH_SOUND_PITCH.asInt();
                        xSound.play(player.getLocation(), volume, pitch);
                    }
                });
                fight.getSpectators().forEach(spectator -> {
                    if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                        spectator.sendMessage(Config.DEATH_MESSAGE.asString()
                            .replace("<looser>", damaged.getName())
                            .replace("<winner>", opponent.getName()));
                    }
                    if (Config.DEATH_SOUND_SEND_SPECT.asBoolean()) {
                        XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                        if (xSound == null || !xSound.isSupported()) return;
                        int volume = Config.DEATH_SOUND_VOLUME.asInt();
                        int pitch = Config.DEATH_SOUND_PITCH.asInt();
                        xSound.play(spectator.getLocation(), volume, pitch);
                    }
                });
            }
            if (damaged.getLastDamageCause().getCause() == DamageCause.FALL) {
                playersInFight.forEach(player -> {
                    if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                        player.sendMessage(Config.DEATH_MESSAGE_NO_PLAYER.asString()
                            .replace("<player>", damaged.getName())
                            .replace("<opponent>", opponent.getName()));
                    }
                    if (Config.DEATH_SOUND_ENABLED.asBoolean()) {
                        XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                        if (xSound == null || !xSound.isSupported()) return;
                        int volume = Config.DEATH_SOUND_VOLUME.asInt();
                        int pitch = Config.DEATH_SOUND_PITCH.asInt();
                        xSound.play(player.getLocation(), volume, pitch);
                    }
                });
                fight.getSpectators().forEach(spectator -> {
                    if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                        spectator.sendMessage(Config.DEATH_MESSAGE_NO_PLAYER.asString()
                            .replace("<player>", damaged.getName())
                            .replace("<opponent>", opponent.getName()));
                    }
                    if (Config.DEATH_SOUND_SEND_SPECT.asBoolean()) {
                        XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                        if (xSound == null || !xSound.isSupported()) return;
                        int volume = Config.DEATH_SOUND_VOLUME.asInt();
                        int pitch = Config.DEATH_SOUND_PITCH.asInt();
                        xSound.play(spectator.getLocation(), volume, pitch);
                    }
                });
            }
        }
    }

    @EventHandler
    public void handleFFA(PlayerDeathEvent event) {
        if (!Config.DEATH_ENABLED.asBoolean()) return;
        StrikePracticeAPI api = StrikePractice.getAPI();
        Player dead = event.getEntity();
        Player killer = dead.getKiller();
        Fight fight = api.getFight(dead);
        if (!(fight instanceof FFAFight)) return;

        if (killer == null) {
            if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                fight.getPlayersInFight().forEach(player ->
                    player.sendMessage(Config.DEATH_MESSAGE_NO_PLAYER.asString()
                        .replace("<player>", dead.getName())
                ));
            }
            fight.getSpectators().forEach(spectator -> {
                if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                    spectator.sendMessage(Config.DEATH_MESSAGE_NO_PLAYER.asString()
                            .replace("<player>", dead.getName())
                    );
                }
                if (Config.DEATH_SOUND_SEND_SPECT.asBoolean()) {
                    XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                    if (xSound == null || !xSound.isSupported()) return;
                    int volume = Config.DEATH_SOUND_VOLUME.asInt();
                    int pitch = Config.DEATH_SOUND_PITCH.asInt();
                    xSound.play(spectator.getLocation(), volume, pitch);
                }
            });
            return;
        }
        fight.getPlayersInFight().forEach(player -> {
            if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                player.sendMessage(Config.DEATH_MESSAGE.asString()
                        .replace("<winner>", killer.getName())
                        .replace("<looser>", dead.getName())
                );
            }
            if (Config.DEATH_SOUND_ENABLED.asBoolean()) {
                XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                if (xSound == null || !xSound.isSupported()) return;
                int volume = Config.DEATH_SOUND_VOLUME.asInt();
                int pitch = Config.DEATH_SOUND_PITCH.asInt();
                xSound.play(player.getLocation(), volume, pitch);
            }
        });
        fight.getSpectators().forEach(spectator -> {
            if (!Config.DEATH_DISABLE_MESSAGE.asBoolean()) {
                spectator.sendMessage(Config.DEATH_MESSAGE.asString()
                        .replace("<winner>", killer.getName())
                        .replace("<looser>", dead.getName())
                );
            }
            if (Config.DEATH_SOUND_SEND_SPECT.asBoolean()) {
                XSound xSound = XSound.matchXSound(Config.DEATH_SOUND_VALUE.asString()).orElse(null);
                if (xSound == null || !xSound.isSupported()) return;
                int volume = Config.DEATH_SOUND_VOLUME.asInt();
                int pitch = Config.DEATH_SOUND_PITCH.asInt();
                xSound.play(spectator.getLocation(), volume, pitch);
            }
        });
    }
}