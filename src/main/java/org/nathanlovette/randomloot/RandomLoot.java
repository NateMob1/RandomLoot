package org.nathanlovette.randomloot;

import org.bukkit.plugin.java.JavaPlugin;
import org.nathanlovette.randomloot.menucommand.RandomLootMenuCommand;
import org.nathanlovette.randomloot.objectcommand.RandomLootObjectCommand;
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
        Objects.requireNonNull(this.getCommand("randomlootmenu")).setExecutor(new RandomLootMenuCommand(this));
        Objects.requireNonNull(this.getCommand("randomlootobj")).setExecutor(new RandomLootObjectCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
