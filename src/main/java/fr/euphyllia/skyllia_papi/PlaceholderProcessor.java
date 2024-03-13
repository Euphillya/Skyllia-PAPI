package fr.euphyllia.skyllia_papi;

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

    public static String processor(UUID playerId, String placeholder) throws ExecutionException, InterruptedException, TimeoutException {
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
}
