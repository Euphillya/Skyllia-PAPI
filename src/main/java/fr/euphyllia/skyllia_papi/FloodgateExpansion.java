package fr.euphyllia.skyllia_papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class FloodgateExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "skyllia-papi";
    }

    @Override
    public String getRequiredPlugin() {
        return "Skyllia";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Euphyllia";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        if (player == null) return "";
        try {
            return PlaceholderProcessor.processor(player.getUniqueId(), placeholder);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            return "";
        }
    }
}