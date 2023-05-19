package org.nathanlovette.randomloot.menucommand;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    private Inventory guiMenu;

    public EventListener(Inventory guiMenu) {
        this.guiMenu = guiMenu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            event.setCancelled(true); // Prevent normal inventory interaction

            Player player = (Player) event.getWhoClicked();

            int clickedSlot = event.getSlot();
            ItemStack clickedItem = guiMenu.getItem(clickedSlot); // Get the item in the clicked slot

        }
    }

    // Other event handler methods...
}
