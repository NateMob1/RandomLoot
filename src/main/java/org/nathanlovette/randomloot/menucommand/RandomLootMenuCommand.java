package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
//        RandomLootMainMenu randomLootMainMenu = new RandomLootMainMenu();
        Inventory guiMenu = Bukkit.createInventory(null, 9, "Random Loot Menu");
//
//        // Fill the GUI menu with items
//        for (MenuButton button : randomLootMainMenu.buttons) {
//            // Perform operations with each button
//            guiMenu.setItem(button.slot, button.toItemStack());
//        }
        ItemStack chestItemStack = new ItemStack(Material.CHEST);
        ItemMeta chestItemMeta = chestItemStack.getItemMeta();
        chestItemMeta.setDisplayName("Chest Info");
        guiMenu.addItem(chestItemStack);


        ItemStack appleItemStack = new ItemStack(Material.APPLE);
        ItemMeta appleItemMeta = chestItemStack.getItemMeta();
        appleItemMeta.setDisplayName("Item Info");
        guiMenu.addItem(appleItemStack);

        // Register the event listener
        getServer().getPluginManager().registerEvents(new MenuEventListener(guiMenu), plugin);

        // Open the GUI menu
        Player player = (Player) sender;
        player.openInventory(guiMenu);

        return true;
    }
}
