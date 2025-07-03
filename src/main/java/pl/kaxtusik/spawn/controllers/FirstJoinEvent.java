package pl.kaxtusik.spawn.controllers;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.config.Config;
import pl.kaxtusik.spawn.manager.SpawnManager;

@RequiredArgsConstructor
public class FirstJoinEvent implements Listener {

    private final Plugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Config config = plugin.getPluginConfig();
        SpawnManager spawnManager = plugin.getSpawnManager();

        if (!config.isTeleportOnFirstJoin()) {
            return;
        }

        final Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            return;
        }

        spawnManager.addToTeleport(player.getUniqueId(), System.currentTimeMillis() + (config.getTimeToTeleport() * 1000L));
    }
}
