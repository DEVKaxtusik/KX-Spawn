package pl.kaxtusik.spawn.bridge.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import pl.kaxtusik.spawn.bridge.litecommands.models.Command;
import pl.kaxtusik.spawn.bridge.litecommands.models.SubCommand;

public class CommandSerializer implements ObjectSerializer<Command> {
    @Override
    public boolean supports(@NonNull Class<? super Command> type) {
        return type.isAssignableFrom(Command.class);
    }

    @Override
    public void serialize(@NonNull Command object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.name);
        data.add("enabled", object.enabled);
        data.add("aliases", object.aliases);
        data.add("permissions", object.permissions);
        data.add("subCommands", object.subCommands);
    }

    @Override
    public Command deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        Command command = new Command();
        command.name = data.get("name", String.class);
        command.enabled = data.get("enabled", Boolean.class);
        command.aliases = data.getAsList("aliases", String.class);
        command.permissions = data.getAsList("permissions", String.class);
        command.subCommands = data.getAsMap("subCommands", String.class, SubCommand.class);
        return command;
    }
}
