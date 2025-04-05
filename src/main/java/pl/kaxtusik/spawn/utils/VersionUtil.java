package pl.kaxtusik.spawn.utils;

import com.tcoded.folialib.FoliaLib;
import pl.kaxtusik.spawn.Plugin;

public class VersionUtil {

    private static final Plugin plugin = Plugin.INSTANCE;

    public static String getDistribution() {
        final FoliaLib foliaLib = plugin.getFoliaLib();
        if (foliaLib.isFolia()) {
            return "Folia - Bugs and issues may occur if any errors are found please report them to the author of the plugin.";
        } else if (foliaLib.isPaper()) {
            return "Paper";
        } else if (foliaLib.isSpigot()) {
            return "Spigot - Not supported";
        } else {
            return "You are using different distribution than Spigot, Paper or Folia which is not supported by this plugin.";
        }
    }

    public static String getVersion() {
        return plugin.getServer().getVersion();
    }

}
