package com.price.usrreport;

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
        getLogger().info("UserReportPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("UserReportPlugin has been disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        onlinePlayers.add(player.getName());

        getLogger().info("Player joined: " + player.getName());
        getLogger().info("Online players (" + onlinePlayers.size() + "): " + String.join(", ", onlinePlayers));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        onlinePlayers.remove(player.getName());

        getLogger().info("Player left: " + player.getName());
        getLogger().info("Online players (" + onlinePlayers.size() + "): " + String.join(", ", onlinePlayers));
    }
}
