package org.nathanlovette.randomloot;

import org.bukkit.plugin.java.JavaPlugin;
import org.nathanlovette.randomloot.textcommand.RandomLootCommand;
import org.nathanlovette.randomloot.textcommand.RandomLootTabCompleter;

import java.util.Objects;

@SuppressWarnings("unused")
public final class RandomLoot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("randomloot")).setExecutor(new RandomLootCommand());
        Objects.requireNonNull(this.getCommand("randomloot")).setTabCompleter(new RandomLootTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
