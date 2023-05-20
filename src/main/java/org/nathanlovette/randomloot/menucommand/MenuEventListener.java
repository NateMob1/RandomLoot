package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

public class MenuEventListener implements Listener {
    private Inventory guiMenu;

    public MenuEventListener(Inventory guiMenu) {
        this.guiMenu = guiMenu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player;
        if (event.getClickedInventory() != null) {

            player = (Player) event.getWhoClicked();

            int clickedSlot = event.getSlot();

            if (event.getView().getTitle().equals("Random Loot Menu")) {
                event.setCancelled(true); // Prevent normal inventory interaction
                if (clickedSlot == 0) {
                    // Get the ItemMeta of the ItemStack
                    ItemStack clickedItemStack = guiMenu.getItem(clickedSlot); // Get the item in the clicked slot

                    ItemMeta itemMeta = clickedItemStack.getItemMeta();

                    // Set the display name
                    itemMeta.setDisplayName("§cNot Available");
                    // Set the modified ItemMeta back to the ItemStack
                    clickedItemStack.setItemMeta(itemMeta);
                } else if (clickedSlot == 1) {
                    // switch to Item Info
                    Inventory itemGuiMenu = null;
                    itemGuiMenu = Bukkit.createInventory(null, 9 * 4, "Loot Info");
                    ItemStack addItemStack = new ItemStack(Material.ITEM_FRAME);
                    ItemMeta addItemMeta = addItemStack.getItemMeta();
                    addItemMeta.setDisplayName("Add Item");
                    addItemStack.setItemMeta(addItemMeta);
                    itemGuiMenu.setItem((9 * 3) + 4, addItemStack);

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    itemGuiMenu.setItem((9 * 3), backItemStack);

                    for (int i = 0; i < 9; i++) {
                        if (i != 4 && i != 0) {
                            ItemStack glassItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta glassItemMeta = glassItemStack.getItemMeta();
                            glassItemMeta.setDisplayName(" ");
                            glassItemStack.setItemMeta(glassItemMeta);
                            itemGuiMenu.setItem((9 * 3) + i, glassItemStack);
                        }
                    }

                    // Register the event listener
//                getServer().getPluginManager().registerEvents(new MenuEventListener(guiMenu), plugin);

                    // Open the GUI menu
                    player.closeInventory();
                    player.openInventory(itemGuiMenu);
                }
            } else if (event.getView().getTitle().equals("Loot Info")) {
                event.setCancelled(true); // Prevent normal inventory interaction
                if (clickedSlot == (9 * 3)) {
                    // Back
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
                    chestItemStack.setItemMeta(chestItemMeta);
                    guiMenu.addItem(chestItemStack);

                    ItemStack appleItemStack = new ItemStack(Material.APPLE);
                    ItemMeta appleItemMeta = appleItemStack.getItemMeta();
                    appleItemMeta.setDisplayName("Loot Info");
                    appleItemStack.setItemMeta(appleItemMeta);
                    guiMenu.addItem(appleItemStack);

                    ItemStack emeraldItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta emeraldItemMeta = emeraldItemStack.getItemMeta();
                    emeraldItemMeta.setDisplayName("Generate command");
                    emeraldItemStack.setItemMeta(emeraldItemMeta);
                    guiMenu.setItem(8, emeraldItemStack);

                    // Register the event listener
//                    getServer().getPluginManager().registerEvents(new MenuEventListener(guiMenu), plugin);

                    // Open the GUI menu
                    player.closeInventory();
                    player.openInventory(guiMenu);
                } else if (clickedSlot == (9 * 3) + 4) {
                    // Add Item
                    player.closeInventory();
                    player.sendMessage("§cNot implemented yet");
                } else {
                    // actually wait what
                }
            }
        }
    }

    // Other event handler methods...
}
