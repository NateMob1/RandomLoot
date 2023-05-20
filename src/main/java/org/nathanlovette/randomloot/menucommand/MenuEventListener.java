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

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

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
                    // switch to Loot Info
                    Inventory itemGuiMenu = Bukkit.createInventory(null, 9 * 4, "Loot Info");

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

                    // Draw the loot items from TempPlayerStorage.playerData dictionary with player's UUID as key
                    // Get the player's UUID
                    UUID playerUUID = player.getUniqueId();
                    // Get the player's chest from TempPlayerStorage.playerData dictionary
                    Chest playerChest = TempPlayerStorage.playerOpenChests.get(playerUUID);
                    // Get the player's loot items from the chest
                    ArrayList<LootItem> playerItems = playerChest.items;
                    // Draw the items at top of the GUI
                    for (LootItem lootItem : playerItems) {
                        itemGuiMenu.addItem(new ItemStack(lootItem.material));
                    }

                    // Register the event listener
//                getServer().getPluginManager().registerEvents(new MenuEventListener(guiMenu), plugin);

                    // Open the GUI menu
//                    player.closeInventory();
                    player.openInventory(itemGuiMenu);
                }
            } else if (event.getView().getTitle().equals("Loot Info")) {
                event.setCancelled(true); // Prevent normal inventory interaction
                if (clickedSlot == (9 * 3)) {
                    // Back
                    Inventory guiMenu = Bukkit.createInventory(null, 9, "Random Loot Menu");

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
//                    player.closeInventory();
                    player.openInventory(guiMenu);
                } else if (clickedSlot == (9 * 3) + 4) {
                    // Add Item

                    // get the player's UUID
                    UUID playerUUID = player.getUniqueId();
                    // checking if player has an open lootitem in TempPlayerStorage.playerOpenLootItems
                    LootItem openLootItem = TempPlayerStorage.playerOpenLootItems.get(playerUUID);
                    if (openLootItem == null) {
                        TempPlayerStorage.playerOpenLootItems.put(playerUUID, new LootItem());
                        openLootItem = TempPlayerStorage.playerOpenLootItems.get(playerUUID);
                    }

                    Inventory addItemGuiMenu = Bukkit.createInventory(null, 9, "Add Item");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    addItemGuiMenu.setItem(0, backItemStack);

                    int minimumAmountValue = openLootItem.minAmount;
                    ItemStack minimumSignItemStack = new ItemStack(Material.OAK_SIGN);
                    ItemMeta minimumSignItemMeta = minimumSignItemStack.getItemMeta();
                    minimumSignItemMeta.setDisplayName(String.format("Minimum amount (Currently %d)", minimumAmountValue));
                    minimumSignItemStack.setItemMeta(minimumSignItemMeta);
                    addItemGuiMenu.setItem(2, minimumSignItemStack);

                    int maximumAmountValue = openLootItem.maxAmount;
                    ItemStack maximumSignItemStack = new ItemStack(Material.OAK_SIGN);
                    ItemMeta maximumSignItemMeta = maximumSignItemStack.getItemMeta();
                    maximumSignItemMeta.setDisplayName(String.format("Maximum amount (Currently %d)", maximumAmountValue));
                    maximumSignItemStack.setItemMeta(maximumSignItemMeta);
                    addItemGuiMenu.setItem(6, maximumSignItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    addItemGuiMenu.setItem(8, confirmItemStack);

                    if (openLootItem.material != null) {
                        ItemStack materialItemStack = new ItemStack(openLootItem.material);
                        ItemMeta materialItemMeta = materialItemStack.getItemMeta();
                        materialItemMeta.setDisplayName("Material");
                        materialItemStack.setItemMeta(materialItemMeta);
                        addItemGuiMenu.setItem(4, materialItemStack);
                    }

                    for (int i = 0; i < 9; i++) {
                        if (i != 0 && i != 2 && i != 6 && i != 4 && i != 8) {
                            ItemStack glassItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta glassItemMeta = glassItemStack.getItemMeta();
                            glassItemMeta.setDisplayName(" ");
                            glassItemStack.setItemMeta(glassItemMeta);
                            addItemGuiMenu.setItem(i, glassItemStack);
                        }
                    }

//                    player.closeInventory();
                    player.openInventory(addItemGuiMenu);
                } else {
                    // actually wait what
                }
            } else if (event.getView().getTitle().equals("Add Item")) {
                if (clickedSlot == 0) {
                    event.setCancelled(true);
                    // Back
                    Inventory guiMenu = Bukkit.createInventory(null, 9, "Random Loot Menu");

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
//                    player.closeInventory();
                    player.openInventory(guiMenu);
                } else if (clickedSlot == 2) {
                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentLootItem.minAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);
                } else if (clickedSlot == 6) {
                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory maxAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Maximum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    maxAmountGuiMenu.setItem(9 * 2, backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    maxAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentLootItem.maxAmount);
                    maxAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    maxAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    maxAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    maxAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    maxAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    maxAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    maxAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(maxAmountGuiMenu);
                }
            } else if (event.getView().getTitle().equals("Minimum Amount")) {
                event.setCancelled(true);
                if (clickedSlot == (9 * 2)) {
                    // Back (Add Item menu)

                    // get the player's UUID
                    UUID playerUUID = player.getUniqueId();
                    // getting player's open lootitem in TempPlayerStorage.playerOpenLootItems
                    LootItem openLootItem = TempPlayerStorage.playerOpenLootItems.get(playerUUID);

                    Inventory addItemGuiMenu = Bukkit.createInventory(null, 9, "Add Item");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    addItemGuiMenu.setItem(0, backItemStack);

                    int minimumAmountValue = openLootItem.minAmount;
                    ItemStack minimumSignItemStack = new ItemStack(Material.OAK_SIGN);
                    ItemMeta minimumSignItemMeta = minimumSignItemStack.getItemMeta();
                    minimumSignItemMeta.setDisplayName(String.format("Minimum amount (Currently %d)", minimumAmountValue));
                    minimumSignItemStack.setItemMeta(minimumSignItemMeta);
                    addItemGuiMenu.setItem(2, minimumSignItemStack);

                    int maximumAmountValue = openLootItem.maxAmount;
                    ItemStack maximumSignItemStack = new ItemStack(Material.OAK_SIGN);
                    ItemMeta maximumSignItemMeta = maximumSignItemStack.getItemMeta();
                    maximumSignItemMeta.setDisplayName(String.format("Maximum amount (Currently %d)", maximumAmountValue));
                    maximumSignItemStack.setItemMeta(maximumSignItemMeta);
                    addItemGuiMenu.setItem(6, maximumSignItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    addItemGuiMenu.setItem(8, confirmItemStack);

                    if (openLootItem.material != null) {
                        ItemStack materialItemStack = new ItemStack(openLootItem.material);
                        ItemMeta materialItemMeta = materialItemStack.getItemMeta();
                        materialItemMeta.setDisplayName("Material");
                        materialItemStack.setItemMeta(materialItemMeta);
                        addItemGuiMenu.setItem(4, materialItemStack);
                    }

                    for (int i = 0; i < 9; i++) {
                        if (i != 0 && i != 2 && i != 6 && i != 4 && i != 8) {
                            ItemStack glassItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta glassItemMeta = glassItemStack.getItemMeta();
                            glassItemMeta.setDisplayName(" ");
                            glassItemStack.setItemMeta(glassItemMeta);
                            addItemGuiMenu.setItem(i, glassItemStack);
                        }
                    }

                    player.openInventory(addItemGuiMenu);
                }
                else if (clickedSlot == 4) {
                    // Confirm button
                    UUID playerUUID = player.getUniqueId();
                    LootItem newLootItem = TempPlayerStorage.playerOpenLootItems.get(playerUUID);
                    newLootItem.minAmount = Objects.requireNonNull(event.getClickedInventory().getItem(clickedSlot)).getAmount();
                    TempPlayerStorage.playerOpenLootItems.put(playerUUID, newLootItem);

                    // back to Add Item menu

                    // get the player's UUID
                    // getting player's open lootitem in TempPlayerStorage.playerOpenLootItems
                    LootItem openLootItem = TempPlayerStorage.playerOpenLootItems.get(playerUUID);

                    Inventory addItemGuiMenu = Bukkit.createInventory(null, 9, "Add Item");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    addItemGuiMenu.setItem(0, backItemStack);

                    int minimumAmountValue = openLootItem.minAmount;
                    ItemStack minimumSignItemStack = new ItemStack(Material.OAK_SIGN, minimumAmountValue);
                    ItemMeta minimumSignItemMeta = minimumSignItemStack.getItemMeta();
                    minimumSignItemMeta.setDisplayName(String.format("Minimum amount (Currently %d)", minimumAmountValue));
                    minimumSignItemStack.setItemMeta(minimumSignItemMeta);
                    addItemGuiMenu.setItem(2, minimumSignItemStack);

                    int maximumAmountValue = openLootItem.maxAmount;
                    ItemStack maximumSignItemStack = new ItemStack(Material.OAK_SIGN, maximumAmountValue);
                    ItemMeta maximumSignItemMeta = maximumSignItemStack.getItemMeta();
                    maximumSignItemMeta.setDisplayName(String.format("Maximum amount (Currently %d)", maximumAmountValue));
                    maximumSignItemStack.setItemMeta(maximumSignItemMeta);
                    addItemGuiMenu.setItem(6, maximumSignItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    addItemGuiMenu.setItem(8, confirmItemStack);

                    if (openLootItem.material != null) {
                        ItemStack materialItemStack = new ItemStack(openLootItem.material);
                        ItemMeta materialItemMeta = materialItemStack.getItemMeta();
                        materialItemMeta.setDisplayName("Material");
                        materialItemStack.setItemMeta(materialItemMeta);
                        addItemGuiMenu.setItem(4, materialItemStack);
                    }

                    for (int i = 0; i < 9; i++) {
                        if (i != 0 && i != 2 && i != 6 && i != 4 && i != 8) {
                            ItemStack glassItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta glassItemMeta = glassItemStack.getItemMeta();
                            glassItemMeta.setDisplayName(" ");
                            glassItemStack.setItemMeta(glassItemMeta);
                            addItemGuiMenu.setItem(i, glassItemStack);
                        }
                    }

                    player.openInventory(addItemGuiMenu);
                }
                else if (clickedSlot == 9+1) {
                    // -32

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount - 32;
                    if (currentAmount < 1) {
                        currentAmount = 1;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
                else if (clickedSlot == 9+2) {
                    // -10

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount - 10;
                    if (currentAmount < 1) {
                        currentAmount = 1;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
                else if (clickedSlot == 9+3) {
                    // -1

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount - 1;
                    if (currentAmount < 1) {
                        currentAmount = 1;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
                else if (clickedSlot == 9+5) {
                    // +1

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount + 1;
                    if (currentAmount > 64) {
                        currentAmount = 64;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
                else if (clickedSlot == 9+6) {
                    // +10

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount + 10;
                    if (currentAmount > 64) {
                        currentAmount = 64;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
                else if (clickedSlot == 9+7) {
                    // +32

                    int currentAmount = Objects.requireNonNull(event.getClickedInventory().getItem(9+4)).getAmount();

                    currentAmount = currentAmount + 32;
                    if (currentAmount > 64) {
                        currentAmount = 64;
                    }

                    // Minimum amount menu
                    event.setCancelled(true);

                    Inventory minAmountGuiMenu = Bukkit.createInventory(null, 9 * 3, "Minimum Amount");

                    ItemStack backItemStack = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta backItemMeta = backItemStack.getItemMeta();
                    backItemMeta.setDisplayName("Back");
                    backItemStack.setItemMeta(backItemMeta);
                    minAmountGuiMenu.setItem(((9 * 2)), backItemStack);

                    ItemStack confirmItemStack = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmItemMeta = confirmItemStack.getItemMeta();
                    confirmItemMeta.setDisplayName("Confirm");
                    confirmItemStack.setItemMeta(confirmItemMeta);
                    minAmountGuiMenu.setItem(4, confirmItemStack);

                    LootItem currentLootItem = TempPlayerStorage.playerOpenLootItems.get(player.getUniqueId());
                    ItemStack currentItemStack = new ItemStack(currentLootItem.material, currentAmount);
                    minAmountGuiMenu.setItem((9 + 4), currentItemStack);

                    ItemStack minusOneItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta minusOneItemMeta = minusOneItemStack.getItemMeta();
                    minusOneItemMeta.setDisplayName("-1");
                    minusOneItemStack.setItemMeta(minusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 3, minusOneItemStack);

                    ItemStack minusTenItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 10);
                    ItemMeta minusTenItemMeta = minusTenItemStack.getItemMeta();
                    minusTenItemMeta.setDisplayName("-10");
                    minusTenItemStack.setItemMeta(minusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 2, minusTenItemStack);

                    ItemStack minus32ItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
                    ItemMeta minus32ItemMeta = minus32ItemStack.getItemMeta();
                    minus32ItemMeta.setDisplayName("-32");
                    minus32ItemStack.setItemMeta(minus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 1, minus32ItemStack);

                    ItemStack plusOneItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    ItemMeta plusOneItemMeta = plusOneItemStack.getItemMeta();
                    plusOneItemMeta.setDisplayName("+1");
                    plusOneItemStack.setItemMeta(plusOneItemMeta);
                    minAmountGuiMenu.setItem(9 + 5, plusOneItemStack);

                    ItemStack plusTenItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 10);
                    ItemMeta plusTenItemMeta = plusTenItemStack.getItemMeta();
                    plusTenItemMeta.setDisplayName("+10");
                    plusTenItemStack.setItemMeta(plusTenItemMeta);
                    minAmountGuiMenu.setItem(9 + 6, plusTenItemStack);

                    ItemStack plus32ItemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
                    ItemMeta plus32ItemMeta = plus32ItemStack.getItemMeta();
                    plus32ItemMeta.setDisplayName("+32");
                    plus32ItemStack.setItemMeta(plus32ItemMeta);
                    minAmountGuiMenu.setItem(9 + 7, plus32ItemStack);

                    // Open the GUI menu
                    player.openInventory(minAmountGuiMenu);

                }
            }
        }
    }
}
