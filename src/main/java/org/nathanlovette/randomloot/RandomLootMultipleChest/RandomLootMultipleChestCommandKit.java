package org.nathanlovette.randomloot.RandomLootMultipleChest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.nathanlovette.randomloot.util;

public class RandomLootMultipleChestCommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Syntax
        // /randomlootchest <x1> <y1> <z1> <x2> <y2> <z2> <item1> <minAmount1> <maxAmount1> [<item2> <minAmount2> <maxAmount2> ...]
        // Args
        //                  0     1     2   3     4    5     6        7           8            9       10          11      ...

        int[] givenChestCoordinates1 = new int[3];
        int[] givenChestCoordinates2 = new int[3];

        // Parse the coordinates from arguments 0 to 5
        if (!parseCoordinates(args, givenChestCoordinates1, givenChestCoordinates2, commandSender)) {
            return false;
        }

        // Process the item sets starting from index 6
        int numItemSets = (args.length - 6) / 3;
        Material[] items = new Material[numItemSets];
        int[] minAmounts = new int[numItemSets];
        int[] maxAmounts = new int[numItemSets];

        for (int i = 0; i < numItemSets; i++) {
            int argIndex = 6 + (i * 3);
            items[i] = parseMaterial(args[argIndex]);
            minAmounts[i] = parseInteger(args[argIndex + 1]);
            maxAmounts[i] = parseInteger(args[argIndex + 2]);
        }

// Generate random chest coordinates
        int[] randomChestCoordinates = generateRandomCoordinates(givenChestCoordinates1, givenChestCoordinates2);

// Create the chest and add items
        createChestWithItems(commandSender, randomChestCoordinates, items, minAmounts, maxAmounts);

        return true;
    }

    private boolean parseCoordinates(String[] args, int[] givenChestCoordinates1, int[] givenChestCoordinates2, CommandSender commandSender) {
        // Parse the first set of coordinates
        for (int i = 0; i < 3; i++) {
            if (args.length <= i) {
                return false;
            }

            String arg = args[i];
            if (arg.equals("~")) {
                // Handle relative coordinates
                if (commandSender instanceof Player) {
                    Location playerLocation = ((Player) commandSender).getLocation();
                    givenChestCoordinates1[i] = getRelativeCoordinate(arg, i, playerLocation);
                } else if (commandSender instanceof BlockCommandSender) {
                    Location blockLocation = ((BlockCommandSender) commandSender).getBlock().getLocation();
                    givenChestCoordinates1[i] = getRelativeCoordinate(arg, i, blockLocation);
                } else {
                    commandSender.sendMessage("You cannot use relative coordinates from the console.");
                    return false;
                }
            } else if (util.stringIsInteger(arg)) {
                // Parse integer coordinates
                givenChestCoordinates1[i] = Integer.parseInt(arg);
            } else {
                return false;
            }
        }

        // Parse the second set of coordinates
        for (int i = 3; i < 6; i++) {
            if (args.length <= i) {
                return false;
            }

            String arg = args[i];
            if (arg.equals("~")) {
                if (commandSender instanceof Player) {
                    Location playerLocation = ((Player) commandSender).getLocation();
                    givenChestCoordinates2[i - 3] = getRelativeCoordinate(arg, i - 3, playerLocation);
                } else if (commandSender instanceof BlockCommandSender) {
                    Location blockLocation = ((BlockCommandSender) commandSender).getBlock().getLocation();
                    givenChestCoordinates2[i - 3] = getRelativeCoordinate(arg, i - 3, blockLocation);
                } else {
                    commandSender.sendMessage("You cannot use relative coordinates from the console.");
                    return false;
                }
            } else if (util.stringIsInteger(arg)) {
                givenChestCoordinates2[i - 3] = Integer.parseInt(arg);
            } else {
                return false;
            }
        }

        return true;
    }

    private int getRelativeCoordinate(String arg, int index, Location location) {
        switch (index) {
            case 0:
                return location.getBlock().getLocation().getBlockX();
            case 1:
                return location.getBlock().getLocation().getBlockY();
            case 2:
                return location.getBlock().getLocation().getBlockZ();
            default:
                return 0;
        }
    }

    private Material parseMaterial(String arg) {
        return Material.matchMaterial(arg);
    }

    private int parseInteger(String arg) {
        if (util.stringIsInteger(arg)) {
            return Integer.parseInt(arg);
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int[] generateRandomCoordinates(int[] givenChestCoordinates1, int[] givenChestCoordinates2) {
        int[] randomChestCoordinates = new int[3];

        for (int i = 0; i < 3; i++) {
            int min = Math.min(givenChestCoordinates1[i], givenChestCoordinates2[i]);
            int max = Math.max(givenChestCoordinates1[i], givenChestCoordinates2[i]);

            randomChestCoordinates[i] = (int) (Math.random() * (max - min + 1)) + min;
        }

        return randomChestCoordinates;
    }

    private void createChestWithItems(CommandSender commandSender, int[] randomChestCoordinates, Material[] items, int[] minAmounts, int[] maxAmounts) {
        World chestWorld;

        if (commandSender instanceof Player) {
            chestWorld = ((Player) commandSender).getWorld();
        } else if (commandSender instanceof BlockCommandSender) {
            chestWorld = ((BlockCommandSender) commandSender).getBlock().getWorld();
        } else {
            commandSender.sendMessage("This command cannot be executed from the console.");
            return;
        }

        Location chestLocation = new Location(chestWorld, randomChestCoordinates[0], randomChestCoordinates[1], randomChestCoordinates[2]);

        Block chestBlock = chestWorld.getBlockAt(chestLocation);
        chestBlock.setType(Material.CHEST);

        // Check if the block is already a chest
        if (chestBlock.getType() == Material.CHEST) {
            // Clear the existing chest's inventory
            if (chestBlock.getState() instanceof Chest) {
                Chest existingChest = (Chest) chestBlock.getState();
                existingChest.getInventory().clear();
            }
        } else {
            // If the block is not a chest, set it as a new chest
            chestBlock.setType(Material.CHEST);
        }

        int amountOfItems = 0;
        if (chestBlock.getState() instanceof Chest) {
            Chest chest = (Chest) chestBlock.getState();
            Inventory chestInventory = chest.getInventory();

            for (int i = 0; i < items.length; i++) {
                Material item = items[i];
                int minAmount = minAmounts[i];
                int maxAmount = maxAmounts[i];

                if (item != null && minAmount >= 0 && maxAmount >= 0 && minAmount <= maxAmount) {
                    int itemAmount = (int) Math.floor(Math.random() * (maxAmount - minAmount + 1) + minAmount);
                    ItemStack itemStack = new ItemStack(item, itemAmount);
                    chestInventory.addItem(itemStack);
                    amountOfItems += itemAmount;
                }
            }
        }

        String responseMessage = "Created chest at " + randomChestCoordinates[0] + ", " + randomChestCoordinates[1] + ", " + randomChestCoordinates[2] + " with " + amountOfItems + " items.";
        commandSender.sendMessage(responseMessage);
    }
}
