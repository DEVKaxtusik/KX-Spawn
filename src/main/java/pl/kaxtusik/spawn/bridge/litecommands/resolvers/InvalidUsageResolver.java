package pl.kaxtusik.spawn.bridge.litecommands.resolvers;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Messages;
import pl.kaxtusik.spawn.utils.MessagesUtils;

public class InvalidUsageResolver implements InvalidUsageHandler<CommandSender> {

    private final static MessagesUtils messagesUtils = new MessagesUtils();

    private Messages messages;

    public InvalidUsageResolver(Plugin plugin) {
        this.messages = plugin.getMessages();
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();
        messagesUtils.message(sender, messages.getInvalidUsage().getType(), messages.getInvalidUsage().getMessage().replace("{COMMAND}", invocation.name()).replace("{USAGE}", schematic.first()));
    }
}
