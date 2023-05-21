package org.nathanlovette.randomloot;

import org.bukkit.plugin.java.JavaPlugin;
import org.nathanlovette.randomloot.RandomLootChestCommand.RandomLootChestCommandKit;
import org.nathanlovette.randomloot.RandomLootChestCommand.RandomLootChestTabCompleter;
import org.nathanlovette.randomloot.RandomLootCommand.RandomLootCommandKit;
import org.nathanlovette.randomloot.RandomLootCommand.RandomLootTabCompleter;
import org.nathanlovette.randomloot.RandomLootMultipleChest.RandomLootMultipleChestCommandKit;
import org.nathanlovette.randomloot.RandomLootMultipleChest.RandomLootMultipleChestTabCompleter;
import org.nathanlovette.randomloot.RandomLootMultipleChestWeighted.RandomLootMultipleChestWeightedCommandKit;

import java.util.Objects;

@SuppressWarnings("unused")
public final class RandomLoot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("randomloot")).setExecutor(new RandomLootCommandKit());
        Objects.requireNonNull(this.getCommand("randomloot")).setTabCompleter(new RandomLootTabCompleter());
        Objects.requireNonNull(this.getCommand("randomlootchest")).setExecutor(new RandomLootChestCommandKit());
        Objects.requireNonNull(this.getCommand("randomlootchest")).setTabCompleter(new RandomLootChestTabCompleter());
        Objects.requireNonNull(this.getCommand("randomlootmultiplechest")).setExecutor(new RandomLootMultipleChestCommandKit());
        Objects.requireNonNull(this.getCommand("randomlootmultiplechest")).setTabCompleter(new RandomLootMultipleChestTabCompleter());
        Objects.requireNonNull(this.getCommand("randomlootmultiplechestweighted")).setExecutor(new RandomLootMultipleChestWeightedCommandKit());
        Objects.requireNonNull(this.getCommand("randomlootmultiplechestweighted")).setTabCompleter(new RandomLootMultipleChestTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
