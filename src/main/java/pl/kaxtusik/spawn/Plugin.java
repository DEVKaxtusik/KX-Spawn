package pl.kaxtusik.spawn;

import com.tcoded.folialib.FoliaLib;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.scope.Scope;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kaxtusik.spawn.bridge.litecommands.commands.SpawnCommand;
import pl.kaxtusik.spawn.bridge.litecommands.editor.CommandEditor;
import pl.kaxtusik.spawn.bridge.litecommands.resolvers.InvalidUsageResolver;
import pl.kaxtusik.spawn.bridge.litecommands.resolvers.PermissionResolver;
import pl.kaxtusik.spawn.bridge.config.Config;
import pl.kaxtusik.spawn.bridge.config.Messages;
import pl.kaxtusik.spawn.bridge.config.serializer.CommandSerializer;
import pl.kaxtusik.spawn.bridge.config.serializer.MessageSerializer;
import pl.kaxtusik.spawn.bridge.config.serializer.SubCommandSerializer;
import pl.kaxtusik.spawn.listeners.ActionListener;
import pl.kaxtusik.spawn.manager.SpawnManager;
import pl.kaxtusik.spawn.metrics.MetricsLite;
import pl.kaxtusik.spawn.tasks.SpawnTask;
import pl.kaxtusik.spawn.utils.ColorUtils;
import pl.kaxtusik.spawn.utils.MessagesUtils;
import pl.kaxtusik.spawn.utils.VersionUtil;

import java.io.File;

public final class Plugin extends JavaPlugin {

    public static Plugin INSTANCE;
    @Getter
    private FoliaLib foliaLib;
    private final MessagesUtils messagesUtils = new MessagesUtils();
    private Config config;
    @Getter
    private Messages messages;
    @Getter
    private SpawnManager spawnManager;
    private SpawnTask spawnTask;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        INSTANCE = this;
        foliaLib = new FoliaLib(this);
        new ColorUtils();
        this.getLogger().info(VersionUtil.getDistribution() + " " + VersionUtil.getVersion().split("-")[0]);
        loadConfig();
        loadMessages();
        loadManagers();
        startTasks();
        loadCommands();
        this.getServer().getPluginManager().registerEvents(new ActionListener(this),this);
        if (config.isEnableMetrics()) {
            initializeMetrics();
        }
    }

    private void loadConfig() {
        this.config = ConfigManager.create(Config.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(),
                    registry -> registry.register(new MessageSerializer()),
                    registry -> registry.register(new SubCommandSerializer()),
                    registry -> registry.register(new CommandSerializer()));
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    private void loadMessages() {
        this.messages = ConfigManager.create(Messages.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(),registry -> registry.register(new MessageSerializer()));
            it.withBindFile(new File(this.getDataFolder(), "messages.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    private void loadManagers() {
        spawnManager = new SpawnManager(this);
    }

    private void startTasks() {
        spawnTask = new SpawnTask(this);
        foliaLib.getScheduler().runTimer(spawnTask, 0L, 20L);
    }

    private void loadCommands() {
        this.liteCommands = LiteBukkitFactory.builder("kx",this)
                .commands(new SpawnCommand(this))
                .missingPermission(new PermissionResolver(this))
                .invalidUsage(new InvalidUsageResolver(this))
                .editor(Scope.GLOBAL_SCOPE,new CommandEditor(config))
                .build();
    }

    @Override
    public void onDisable() {
        if (liteCommands != null) {
            liteCommands.unregister();
        }
        if (foliaLib != null) {
            foliaLib.getScheduler().cancelAllTasks();
        }
    }

    public void reloadConfiguration(CommandSender sender) {
        foliaLib.getScheduler().runAsync(async -> {
            final long start = System.currentTimeMillis();
            config.load();
            if (sender instanceof Entity entity) {
                foliaLib.getScheduler().runAtEntity(entity, task -> {
                    spawnTask.updateTimeToTeleport(config.getTimeToTeleport());
                    spawnTask.updateCanMove(config.isCanMove());
                    spawnManager.updateSpawnLocation(config.getSpawnLocation());
                    messagesUtils.message(sender, messages.getReloadedConfig().getType(), messages.getReloadedConfig().getMessage());
                    getLogger().info("Configuration reloaded in " + (System.currentTimeMillis() - start) + "ms");
                });
            } else {
                getLogger().info("Configuration reloaded in " + (System.currentTimeMillis() - start) + "ms");
            }
        });
    }

    public void reloadMessages(CommandSender sender) {
        foliaLib.getScheduler().runAsync(async -> {
            final long start = System.currentTimeMillis();
            messages.load();
            if (sender instanceof Entity entity) {
                foliaLib.getScheduler().runAtEntity(entity, task -> {
                    messagesUtils.message(sender, messages.getReloadedMessages().getType(), messages.getReloadedMessages().getMessage());
                    getLogger().info("Messages reloaded in " + (System.currentTimeMillis() - start) + "ms");
                });
            } else {
                getLogger().info("Messages reloaded in " + (System.currentTimeMillis() - start) + "ms");
            }
        });
    }

    private void initializeMetrics() {
        int pluginId = 26366;
        MetricsLite metrics = new MetricsLite(this, pluginId);
    }

    public Config getPluginConfig() {
        return config;
    }

}
