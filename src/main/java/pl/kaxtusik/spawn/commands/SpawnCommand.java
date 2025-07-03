package pl.kaxtusik.spawn.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.config.Config;
import pl.kaxtusik.spawn.config.Messages;
import pl.kaxtusik.spawn.manager.SpawnManager;
import pl.kaxtusik.spawn.utils.MessagesUtils;

import java.util.Optional;

@Command(name = "spawn")
public class SpawnCommand {

    private final Plugin plugin;
    private final Config config;
    private final Messages messages;
    private final SpawnManager spawnManager;
    private static final MessagesUtils MESSAGES_UTILS = new MessagesUtils();

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
        this.messages = plugin.getMessages();
        this.spawnManager = plugin.getSpawnManager();
    }

    @Execute
    void spawn(@Sender Player player, @Arg Optional<Player> target) {
        if (!spawnManager.isSpawnSet()) {
            MESSAGES_UTILS.message(player, messages.getSpawnNotSet().getType(), messages.getSpawnNotSet().getMessage());
            return;
        }

        if (player.hasPermission("kx.spawn.bypass")) {
            MESSAGES_UTILS.message(player, messages.getTeleported().getType(), messages.getTeleported().getMessage());
            plugin.getFoliaLib().getScheduler().teleportAsync(player,config.getSpawnLocation());
            return;
        }

        if (target.isPresent()) {
            if (!player.hasPermission("kx.spawn.others")) {
                MESSAGES_UTILS.message(player, messages.getNoPermission().getType(), messages.getNoPermission().getMessage().replace("{PERMISSION}", "kx.spawn.others"));
                return;
            }
            plugin.getFoliaLib().getScheduler().teleportAsync(target.get(), spawnManager.getSpawnLocation());
            MESSAGES_UTILS.message(target.get(), messages.getTeleported().getType(), messages.getTeleported().getMessage());
            return;
        }

        spawnManager.addToTeleport(player.getUniqueId());
    }

    @Execute(name = "set")
    @Permission("kx.spawn.set")
    void set(@Sender Player player) {
        plugin.getFoliaLib().getScheduler().runAsync(task -> {
            config.setSpawnLocation(player.getLocation());
            config.save();
            spawnManager.updateSpawnLocation(player.getLocation());
        }).thenAccept(accepted -> {
            MESSAGES_UTILS.message(player, messages.getSpawnSet().getType(), messages.getSpawnSet().getMessage());
        });
    }

    @Execute(name = "reload")
    @Permission("kx.spawn.reload")
    void reload(@Sender CommandSender sender, @Arg ReloadType type) {
        switch (type) {
            case CONFIG -> plugin.reloadConfiguration(sender);
            case MESSAGES -> plugin.reloadMessages(sender);
            case ALL -> {
                plugin.reloadConfiguration(sender);
                plugin.reloadMessages(sender);
            }
        }
    }

    enum ReloadType {
        CONFIG,
        MESSAGES,
        ALL
    }
}