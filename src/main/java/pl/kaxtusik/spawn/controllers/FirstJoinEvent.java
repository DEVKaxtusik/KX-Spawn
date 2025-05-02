package pl.kaxtusik.spawn.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.config.Config;
import pl.kaxtusik.spawn.manager.SpawnManager;

public class FirstJoinEvent implements Listener {

    private Config config;
    private SpawnManager spawnManager;

    public FirstJoinEvent(Plugin plugin) {
        this.config = plugin.getPluginConfig();
        this.spawnManager = plugin.getSpawnManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!config.isTeleportOnFirstJoin()) {
            return;
        }
        final Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            return;
        }
        spawnManager.addToTeleport(player.getUniqueId(),System.currentTimeMillis()+(config.getTimeToTeleport()* 1000L));
    }

}
