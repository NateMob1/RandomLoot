package org.nathanlovette.randomloot;

import org.bukkit.plugin.java.JavaPlugin;

public final class RandomLoot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("randomloot").setExecutor(new CommandKit());
        this.getCommand("randomloot").setTabCompleter(new RandomLootTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
