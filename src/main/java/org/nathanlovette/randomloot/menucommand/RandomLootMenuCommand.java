package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class RandomLootMenuCommand implements CommandExecutor {
    private final Plugin plugin;

    public RandomLootMenuCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Create the GUI menu inventory
        Inventory guiMenu = Bukkit.createInventory(null, 9, "My GUI Menu");

        // Fill the GUI menu with items
        ItemStack item1 = new ItemStack(Material.APPLE);
        guiMenu.setItem(0, item1);

        ItemStack item2 = new ItemStack(Material.BREAD);
        guiMenu.setItem(1, item2);

        // Register the event listener
        getServer().getPluginManager().registerEvents(new EventListener(), plugin);

        // Open the GUI menu
        Player player = (Player) sender;
        player.openInventory(guiMenu);

        return true;
    }
}
