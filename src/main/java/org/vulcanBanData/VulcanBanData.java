package org.vulcanBanData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.frep.vulcan.api.VulcanAPI;
import me.frep.vulcan.api.event.VulcanPunishEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VulcanBanData extends JavaPlugin implements Listener {

    private VulcanAPI api;
    private File dataFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void onEnable() {
        api = VulcanAPI.Factory.getApi();
        if (api == null) {
            getLogger().severe("Vulcan API not found! Make sure Vulcan is installed and API is enabled in config.");
            return;
        }

        getLogger().info("VulcanBanData enabled successfully!");
        Bukkit.getPluginManager().registerEvents(this, this);

        // Create JSON file if it doesn't exist
        dataFile = new File(getDataFolder(), "punishments.json");
        if (!dataFile.exists()) {
            try {
                getDataFolder().mkdirs();
                dataFile.createNewFile();
                try (FileWriter writer = new FileWriter(dataFile)) {
                    writer.write("[]"); // Initialize with empty JSON array
                }
            } catch (IOException e) {
                getLogger().severe("Failed to create punishments.json: " + e.getMessage());
            }
        }
    }

    @EventHandler
    public void onVulcanPunish(VulcanPunishEvent event) {
        Player player = event.getPlayer();
        String reason = event.getCheck().getName();
        String timestamp = LocalDateTime.now().format(formatter);

        JsonObject logEntry = new JsonObject();
        logEntry.addProperty("timestamp", timestamp);
        logEntry.addProperty("player", player.getName());
        logEntry.addProperty("reason", reason);

        savePunishment(logEntry);
    }

    private void savePunishment(JsonObject logEntry) {
        try {
            JsonArray punishments = new JsonArray();

            if (dataFile.exists() && dataFile.length() > 0) {
                try (FileReader reader = new FileReader(dataFile)) {
                    punishments = JsonParser.parseReader(reader).getAsJsonArray();
                }
            }

            punishments.add(logEntry);

            try (FileWriter writer = new FileWriter(dataFile)) {
                writer.write(punishments.toString());
            }

        } catch (IOException e) {
            getLogger().severe("Error saving punishment data: " + e.getMessage());
        }
    }
}
