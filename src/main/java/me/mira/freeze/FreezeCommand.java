package me.mira.freeze;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class FreezeCommand implements CommandExecutor, Listener {

    private Freeze plugin;
    private Set<String> frozenPlayers;

    public FreezeCommand(Freeze plugin) {
        this.plugin = plugin;
        frozenPlayers = new HashSet<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("freeze")) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    if (frozenPlayers.contains(target.getName())) {
                        unfreezePlayer(target);
                        sender.sendMessage(target.getName() + " has been unfrozen.");
                    } else {
                        freezePlayer(target);
                        sender.sendMessage(target.getName() + " has been frozen.");
                    }
                } else {
                    // Spieler ist nicht online oder auf dem Server
                    sender.sendMessage("Player " + args[0] + " is not online!");
                }
                return true;
            }
        }
        return false;
    }

    private void freezePlayer(Player player) {
        frozenPlayers.add(player.getName());
        sendFrozenMessage(player);
    }
    private void unfreezePlayer(Player player) {
        frozenPlayers.remove(player.getName());
    }

    private void sendFrozenMessage(Player player) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (frozenPlayers.contains(player.getName())) {
                    player.sendMessage("§cYou are frozen!");
                    player.sendTitle("You are frozen!", "", 0, 20, 10);
                } else {
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, 0, 100); // Sendet die Nachricht alle 5 Sekunden (100 Ticks)
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (frozenPlayers.contains(player.getName())) {
            event.setCancelled(true);
        }
    }
}
