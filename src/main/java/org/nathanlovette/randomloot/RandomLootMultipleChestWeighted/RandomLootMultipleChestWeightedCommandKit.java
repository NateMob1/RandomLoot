package org.nathanlovette.randomloot.RandomLootMultipleChestWeighted;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomLootMultipleChestWeightedCommandKit implements CommandExecutor {
    private int randomRange(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Syntax
        // /randomlootmultiplechestweighted <x1> <y1> <z1> <x2> <y2> <z2> <item1> <minAmount1> <maxAmount1> [<item2> <minAmount2> <maxAmount2> ...]
        // Args
        //                          0      1     2    3     4    5      6       7          8           9        10         11      ...

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

        // Get the minChestY and maxChestY values
        int minChestY = Math.min(givenChestCoordinates1[1], givenChestCoordinates2[1]);
        int maxChestY = Math.max(givenChestCoordinates1[1], givenChestCoordinates2[1]);

        // Create the chest and add items
        createChestWithItems(commandSender, randomChestCoordinates, items, minAmounts, maxAmounts, minChestY, maxChestY);

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
                    Player player = (Player) commandSender;
                    Location location = player.getLocation();
                    givenChestCoordinates1[i] = location.getBlockX();
                } else if (commandSender instanceof BlockCommandSender) {
                    BlockCommandSender blockCommandSender = (BlockCommandSender) commandSender;
                    Block block = blockCommandSender.getBlock();
                    givenChestCoordinates1[i] = block.getX();
                } else {
                    return false;
                }
            } else {
                givenChestCoordinates1[i] = parseInteger(arg);
            }
        }

        // Parse the second set of coordinates
        for (int i = 0; i < 3; i++) {
            int argIndex = i + 3;
            if (args.length <= argIndex) {
                return false;
            }

            String arg = args[argIndex];
            if (arg.equals("~")) {
                // Handle relative coordinates
                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;
                    Location location = player.getLocation();
                    givenChestCoordinates2[i] = location.getBlockX();
                } else if (commandSender instanceof BlockCommandSender) {
                    BlockCommandSender blockCommandSender = (BlockCommandSender) commandSender;
                    Block block = blockCommandSender.getBlock();
                    givenChestCoordinates2[i] = block.getX();
                } else {
                    return false;
                }
            } else {
                givenChestCoordinates2[i] = parseInteger(arg);
            }
        }

        return true;
    }

    private Material parseMaterial(String materialName) {
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            throw new IllegalArgumentException("Invalid material name: " + materialName);
        }
        return material;
    }

    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer value: " + value);
        }
    }

    private int[] generateRandomCoordinates(int[] givenChestCoordinates1, int[] givenChestCoordinates2) {
        int minX = Math.min(givenChestCoordinates1[0], givenChestCoordinates2[0]);
        int minY = Math.min(givenChestCoordinates1[1], givenChestCoordinates2[1]);
        int minZ = Math.min(givenChestCoordinates1[2], givenChestCoordinates2[2]);
        int maxX = Math.max(givenChestCoordinates1[0], givenChestCoordinates2[0]);
        int maxY = Math.max(givenChestCoordinates1[1], givenChestCoordinates2[1]);
        int maxZ = Math.max(givenChestCoordinates1[2], givenChestCoordinates2[2]);

        int randomX = randomRange(minX, maxX);
        int randomY = randomRange(minY, maxY);
        int randomZ = randomRange(minZ, maxZ);

        return new int[]{randomX, randomY, randomZ};
    }

    private void createChestWithItems(CommandSender commandSender, int[] randomChestCoordinates, Material[] items, int[] minAmounts, int[] maxAmounts, int minChestY, int maxChestY) {
        World world;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            world = player.getWorld();
        } else if (commandSender instanceof BlockCommandSender) {
            BlockCommandSender blockCommandSender = (BlockCommandSender) commandSender;
            Block block = blockCommandSender.getBlock();
            world = block.getWorld();
        } else {
            return;
        }

        Location chestLocation = new Location(world, randomChestCoordinates[0], randomChestCoordinates[1], randomChestCoordinates[2]);
        Block chestBlock = world.getBlockAt(chestLocation);

        // Check if the chest block is already a chest
        if (chestBlock.getType() != Material.CHEST) {
            chestBlock.setType(Material.CHEST);
        }

        Chest chest = (Chest) chestBlock.getState();
        Inventory chestInventory = chest.getBlockInventory();

        List<ItemStack> itemsToAdd = new ArrayList<>();

        List<String> itemMessages = new ArrayList<>(); // Store messages for each item added

        // Add items to the list
        for (int i = 0; i < items.length; i++) {
            Material item = items[i];
            int minAmount = minAmounts[i];
            int maxAmount = maxAmounts[i];

            // Calculate the relative Y coordinate within the possible chest placement range
            int relativeY = randomChestCoordinates[1] - minChestY;

            // Adjust the range of possible quantities based on the relative Y coordinate
            int range = maxChestY - minChestY + 1; // Total range of possible Y values
            int adjustedMinAmount = (int) Math.round(minAmount * (1.0 - (double) relativeY / range)); // Adjusted minimum amount based on the relative Y coordinate
            int adjustedMaxAmount = (int) Math.round(maxAmount * (1.0 - (double) relativeY / range)); // Adjusted maximum amount based on the relative Y coordinate

            int randomAmount = randomRange(adjustedMinAmount, adjustedMaxAmount);

            ItemStack itemStack = new ItemStack(item, randomAmount);
            itemsToAdd.add(itemStack);

            // Create a message for the item
            String itemMessage = String.format("%s: %d", item.toString(), randomAmount);
            itemMessages.add(itemMessage);
        }

        // Shuffle the list of items
        Collections.shuffle(itemsToAdd);

        // Add items to the chest inventory
        for (ItemStack itemStack : itemsToAdd) {
            if (chestInventory.firstEmpty() != -1) {
                chestInventory.addItem(itemStack);
            } else {
                world.dropItemNaturally(chestLocation, itemStack);
            }
        }

        // Create the message for added items
        String itemAddedMessage = String.join(", ", itemMessages);

        commandSender.sendMessage("Created chest at " + randomChestCoordinates[0] + ", " + randomChestCoordinates[1] + ", " + randomChestCoordinates[2] +
                " with items: " + itemAddedMessage);
    }
}
