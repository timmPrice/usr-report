package com.price.usrreport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Report extends JavaPlugin implements Listener {
    private final Set<String> onlinePlayers = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("USR-REPORT : enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("USR-REPORT : disabled");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        onlinePlayers.add(player.getName());

        getLogger().info("Player joined: " + player.getName());
        sendPlayerListToWebsite(onlinePlayers);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        onlinePlayers.remove(player.getName());

        getLogger().info("Player left: " + player.getName());
        sendPlayerListToWebsite(onlinePlayers);
    }
    
    public void sendPlayerListToWebsite(Set<String> onlinePlayers) {
        try {
            URL url = new URL("http://localhost:3000/api/active"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String json = "{\"players\": [\"" + String.join("\",\"", onlinePlayers) + "\"]}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                getLogger().info("sending json " + json);
            }

            int code = connection.getResponseCode();
            getLogger().info("POST response code: " + code);
            
        } catch (Exception e) {
                getLogger().severe("Failed to send player list to website: " + e.getMessage());
        }
    }
}
