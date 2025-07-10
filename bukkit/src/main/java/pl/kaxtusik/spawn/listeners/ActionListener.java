package pl.kaxtusik.spawn.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Config;
import pl.kaxtusik.spawn.manager.SpawnManager;

@RequiredArgsConstructor
public class ActionListener implements Listener {

    private final Plugin plugin;

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        Config config = plugin.getPluginConfig();

        if (config.isTeleportOnFirstJoin() && !player.hasPlayedBefore()) {
            teleportPlayerToSpawn(player);
        }

        if (config.isTeleportOnEveryJoin()) {
            teleportPlayerToSpawn(player);
        }
    }

    @EventHandler
    public void handleCombat(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!plugin.getPluginConfig().isCanBeDamaged()) {
            return;
        }

        cancelTeleportIfPending(player);

        if (event.getDamager() instanceof Player damager) {
            cancelTeleportIfPending(damager);
        }
    }

    @EventHandler
    public void handleRespawn(PlayerRespawnEvent event) {
        if (!plugin.getPluginConfig().isRespawnOnSpawn()) {
            return;
        }

        teleportPlayerToSpawn(event.getPlayer());
    }


    private void teleportPlayerToSpawn(Player player) {
        plugin.getFoliaLib().getScheduler().teleportAsync(player, plugin.getSpawnManager().getSpawnLocation());
    }

    private void cancelTeleportIfPending(Player player) {
        SpawnManager spawnManager = plugin.getSpawnManager();
        if (spawnManager.getToTeleport().containsKey(player.getUniqueId())) {
            spawnManager.removeFromTeleport(player.getUniqueId());
        }
    }
}
