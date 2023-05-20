package org.nathanlovette.randomloot;

import org.bukkit.plugin.java.JavaPlugin;
import org.nathanlovette.randomloot.RandomLootCommand.RandomLootCommandKit;
import org.nathanlovette.randomloot.RandomLootCommand.RandomLootTabCompleter;

import java.util.Objects;

@SuppressWarnings("unused")
public final class RandomLoot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("randomloot")).setExecutor(new RandomLootCommandKit());
        Objects.requireNonNull(this.getCommand("randomloot")).setTabCompleter(new RandomLootTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
