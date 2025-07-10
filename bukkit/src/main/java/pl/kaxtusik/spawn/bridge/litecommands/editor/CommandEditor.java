package pl.kaxtusik.spawn.bridge.litecommands.editor;

import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import org.bukkit.command.CommandSender;
import pl.kaxtusik.spawn.bridge.config.Config;
import pl.kaxtusik.spawn.bridge.litecommands.models.Command;
import pl.kaxtusik.spawn.bridge.litecommands.models.SubCommand;

public class CommandEditor implements Editor<CommandSender> {

    private final Config config;

    public CommandEditor(Config config) {
        this.config = config;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        Command command = config.getCommand(context.name());

        for (String childName : command.getSubCommands().keySet()) {
            SubCommand subCommand = command.getSubCommands().get(childName);

            context = context.editChild(childName, editor -> editor
                    .name(subCommand.getName())
                    .aliases(subCommand.getAliases())
                    .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions ->
                            permissions.addAll(new PermissionSet(subCommand.getPermissions()))))
                    .enabled(subCommand.isEnabled())
            );
        }

        return context
                .name(command.getName())
                .aliases(command.getAliases())
                .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions ->
                        permissions.addAll(new PermissionSet(command.getPermissions()))))
                .enabled(command.isEnabled());
    }
}
