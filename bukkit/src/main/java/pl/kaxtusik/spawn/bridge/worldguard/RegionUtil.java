package pl.kaxtusik.spawn.bridge.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RegionUtil {

    public static boolean isLocationInRegion(Location location, String regionName) {
        Set<ProtectedRegion> regions = getRegionsAtLocation(location);
        return regions.stream().anyMatch(region -> region.getId().equalsIgnoreCase(regionName));
    }

    public static boolean isPlayerInAnyRegion(Player player, List<String> regions) {
        final Location location = player.getLocation();
        return regions.stream().anyMatch(region -> isLocationInRegion(location, region));
    }

    public static Set<ProtectedRegion> getRegionsAtLocation(Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regions == null) {
            return Collections.emptySet();
        }

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        ApplicableRegionSet set = regions.getApplicableRegions(vector);

        return set.getRegions();
    }
}
