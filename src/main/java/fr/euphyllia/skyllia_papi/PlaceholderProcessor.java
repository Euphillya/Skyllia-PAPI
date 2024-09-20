package fr.euphyllia.skyllia_papi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.euphyllia.skyllia.api.SkylliaAPI;
import fr.euphyllia.skyllia.api.skyblock.Island;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PlaceholderProcessor {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static final LoadingCache<CacheKey, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<>() {
                @Override
                public @NotNull String load(@NotNull CacheKey cacheKey) throws Exception {
                    return processorIsland(cacheKey.playerId(), cacheKey.placeholder());
                }
            });

    public static String processor(UUID playerId, String placeholder) throws ExecutionException, InterruptedException, TimeoutException {
        return cache.getUnchecked(new CacheKey(playerId, placeholder));
    }

    private static String processorIsland(UUID playerId, String placeholder) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<@NotNull Island> future = SkylliaAPI.getIslandByPlayerId(playerId);
        if (future == null) return "";
        Island island = future.get(5, TimeUnit.SECONDS);

        return switch (placeholder.toLowerCase(Locale.ROOT)) {
            case "island_size" -> String.valueOf(island.getSize());
            case "island_members_max_size" -> String.valueOf(island.getMaxMembers());
            case "island_members_size" -> String.valueOf(island.getMembers().size());
            case "island_rank" -> island.getMember(playerId).getRoleType().name();
            case "island_tps" -> {
                Player player = Bukkit.getPlayer(playerId);
                if (player == null) yield String.valueOf(-1.0);
                if (!SkylliaAPI.isWorldSkyblock(player.getWorld())) yield String.valueOf(-1.0);
                double @Nullable [] value = SkylliaAPI.getTPS(player.getChunk());
                if (value == null) yield String.valueOf(-1.0);
                yield df.format(value[0]);
            }
            default -> "";
        };
    }

    private record CacheKey(UUID playerId, String placeholder){}
}
