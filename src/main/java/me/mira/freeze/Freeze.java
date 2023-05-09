package me.mira.freeze;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Freeze extends JavaPlugin {
    Logger log = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        var freeze = getCommand("freeze");
        freeze.setExecutor(new FreezeCommand(this));

        log.info("Glückwunsch Plugin funktioniert!.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
