package pl.kaxtusik.spawn.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessagesUtils {

    public void message(CommandSender sender, String type, String message) {
        if (sender instanceof Player p) {
            String msgType = type.toUpperCase();

            switch (msgType) {
                case "TITLE":
                    p.showTitle(Title.title(ColorUtils.formatToComponent(message), Component.empty()));
                    break;

                case "TITLE_SUBTITLE":
                    String[] split = message.replace("\\n", "\n").split("\n", 2);
                    Component title = ColorUtils.formatToComponent(split[0]);
                    Component subtitle = split.length > 1 ? ColorUtils.formatToComponent(split[1]) : Component.empty();
                    p.showTitle(Title.title(title, subtitle));
                    break;

                case "SUBTITLE":
                    p.showTitle(Title.title(Component.empty(), ColorUtils.formatToComponent(message)));
                    break;

                case "ACTIONBAR":
                    p.sendActionBar(ColorUtils.formatToComponent(message));
                    break;

                case "CHAT":
                    p.sendMessage(ColorUtils.formatToComponent(message));
                    break;

                default:
                    sender.sendMessage(ColorUtils.formatToComponent(message));
                    break;
            }
        } else {
            sender.sendMessage(ColorUtils.formatToComponent(message));
        }
    }
}
