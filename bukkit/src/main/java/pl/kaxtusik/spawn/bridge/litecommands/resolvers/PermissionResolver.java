package pl.kaxtusik.spawn.bridge.litecommands.resolvers;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Messages;
import pl.kaxtusik.spawn.utils.MessagesUtils;

public class PermissionResolver implements MissingPermissionsHandler<CommandSender> {

    private final static MessagesUtils messagesUtils = new MessagesUtils();

    private final Messages messages;

    public PermissionResolver(Plugin plugin) {
        this.messages = plugin.getMessages();
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        final CommandSender sender = invocation.sender();
        messagesUtils.message(sender,messages.getNoPermission().getType(), messages.getNoPermission().getMessage().replace("{PERMISSION}", missingPermissions.asJoinedText()));
    }
}
