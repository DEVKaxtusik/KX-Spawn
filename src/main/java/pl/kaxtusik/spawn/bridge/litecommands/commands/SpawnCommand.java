package pl.kaxtusik.spawn.bridge.litecommands.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Config;
import pl.kaxtusik.spawn.bridge.config.Messages;
import pl.kaxtusik.spawn.bridge.worldguard.RegionUtil;
import pl.kaxtusik.spawn.manager.SpawnManager;
import pl.kaxtusik.spawn.utils.MessagesUtils;

import java.util.Optional;

@Command(name = "spawn")
@Description("Main spawn command for teleporting players")
public class SpawnCommand {

    private final Plugin plugin;
    private final Config config;
    private final Messages messages;
    private final SpawnManager spawnManager;
    private static final MessagesUtils MESSAGES_UTILS = new MessagesUtils();
    private final String teleportOthers;
    private final String teleportBypass;

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
        teleportOthers = config.getTeleportOthersPermission();
        teleportBypass = config.getTeleportBypassPermission();
        this.messages = plugin.getMessages();
        this.spawnManager = plugin.getSpawnManager();
    }

    @Execute
    @Description("Teleport yourself or another player to spawn")
    void spawn(@Sender Player player, @Arg Optional<Player> target) {
        if (!spawnManager.isSpawnSet()) {
            System.out.println(spawnManager.getSpawnLocation());
            MESSAGES_UTILS.message(player, messages.getSpawnNotSet().getType(), messages.getSpawnNotSet().getMessage());
            return;
        }

        if (target.isPresent()) {
            if (!player.hasPermission(teleportOthers)) {
                MESSAGES_UTILS.message(player, messages.getNoPermission().getType(),
                        messages.getNoPermission().getMessage().replace("{PERMISSION}", teleportOthers));
                return;
            }
            plugin.getFoliaLib().getScheduler().teleportAsync(target.get(), spawnManager.getSpawnLocation());
            MESSAGES_UTILS.message(target.get(), messages.getTeleported().getType(), messages.getTeleported().getMessage());
            MESSAGES_UTILS.message(player, messages.getTeleportingOthers().getType(),
                    messages.getTeleportingOthers().getMessage().replace("{PLAYER}", target.get().getName()));
            return;
        }

        boolean hasBypass = player.hasPermission(teleportBypass);
        boolean isInInstantRegion = RegionUtil.isPlayerInAnyRegion(player, spawnManager.getInstantRegions());

        if (hasBypass || isInInstantRegion) {
            plugin.getFoliaLib().getScheduler().teleportAsync(player, spawnManager.getSpawnLocation());
            MESSAGES_UTILS.message(player, messages.getTeleported().getType(), messages.getTeleported().getMessage());
            return;
        }

        spawnManager.addToTeleport(player.getUniqueId());
    }


    @Execute(name = "set")
    @Description("Set the spawn location to your current position")
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
    @Description("Reload plugin configuration and messages")
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