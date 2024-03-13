package fr.euphyllia.skyllia_papi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.euphyllia.skyllia.api.SkylliaAPI;
import fr.euphyllia.skyllia.api.skyblock.Island;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PlaceholderProcessor {

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
            default -> "";
        };
    }

    private record CacheKey(UUID playerId, String placeholder){}
}
