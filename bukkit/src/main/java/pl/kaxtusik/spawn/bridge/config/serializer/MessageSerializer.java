package pl.kaxtusik.spawn.bridge.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.kaxtusik.spawn.models.Message;

public class MessageSerializer implements ObjectSerializer<Message> {
    @Override
    public boolean supports(Class<? super Message> type) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Message object, SerializationData data, GenericsDeclaration generics) {
        data.add("type", object.getType());
        data.add("message", object.getMessage());
    }

    @Override
    public Message deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new Message(data.get("type", String.class), data.get("message", String.class));
    }
}
