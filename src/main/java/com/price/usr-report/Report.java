package com.price.usrreport;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Report extends JavaPlugin implements Listener {
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
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        Component welcomeMessage = Component.text()
                .append(Component.text("Welcome, ", NamedTextColor.GOLD))
                .append(player.name().color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
                .append(Component.text("! There are now ", NamedTextColor.GOLD))
                .append(Component.text(onlinePlayers, NamedTextColor.AQUA).decorate(TextDecoration.BOLD))
                .append(Component.text(" players online.", NamedTextColor.GOLD))
                .build();

        player.sendMessage(welcomeMessage);

        Component broadcastMessage = Component.text()
                .append(player.name().color(NamedTextColor.GREEN))
                .append(Component.text(" has joined the server!", NamedTextColor.GRAY))
                .build();

        event.joinMessage(null); 

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                onlinePlayer.sendMessage(broadcastMessage);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        int onlinePlayersAfterQuit = Bukkit.getOnlinePlayers().size() - 1; 

        if (onlinePlayersAfterQuit < 0) {
            onlinePlayersAfterQuit = 0;
        }

        Component farewellMessage = Component.text()
                .append(Component.text("Goodbye, ", NamedTextColor.RED))
                .append(player.name().color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
                .append(Component.text("! There are now ", NamedTextColor.RED))
                .append(Component.text(onlinePlayersAfterQuit, NamedTextColor.AQUA).decorate(TextDecoration.BOLD))
                .append(Component.text(" players online.", NamedTextColor.RED))
                .build();

        event.quitMessage(null); 

        Bukkit.broadcast(farewellMessage);
    }
}
