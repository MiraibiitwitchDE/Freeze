package me.mira.freeze;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerMoveListener implements Listener {

    private Player player;
    private boolean hasMoved;

    public PlayerMoveListener(Player player) {
        this.player = player;
        this.hasMoved = false;
    }

    public void sendMessage(String message) {
        if (hasMoved) {
            player.sendMessage(Component.text(message));
        }
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (hasMoved) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public void unregister() {
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerMoveEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() == player) {
            hasMoved = true;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer() == player) {
            unregister();
        }
    }
}

