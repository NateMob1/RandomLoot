package org.nathanlovette.randomloot.RandomLootChestCommand;

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

import java.util.Random;

public class RandomLootChestCommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        // Syntax
        // /randomlootchest <x1> <y1> <z1> <x2> <y2> <z2> <item> <minAmount> <maxAmount>
        // Args
        //                  0     1     2   3     4    5     6         7         8

        int[] givenChestCoordinates1 = new int[3];
        int[] givenChestCoordinates2 = new int[3];

        Material givenItem;

        int givenMinAmount;

        int givenMaxAmount;


        // is args[0 through 2] integers (coordinates)?
        for (int i = 0; i < 3; i++) {
            String arg = args[i];

            if (arg.equals("~")) {
                if (commandSender instanceof Player) {
                    // Use the player's current location for relative coordinates
                    Location playerLocation = ((Player) commandSender).getLocation();
                    if (i == 0) {
                        givenChestCoordinates1[i] = playerLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 1) {
                        givenChestCoordinates1[i] = playerLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenChestCoordinates1[i] = playerLocation.getBlock().getLocation().getBlockZ();
                    }

                } else if (commandSender instanceof BlockCommandSender) {
                    // Use the command block's location for relative coordinates
                    Location blockLocation = ((BlockCommandSender) commandSender).getBlock().getLocation();
                    if (i == 0) {
                        givenChestCoordinates1[i] = blockLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 1) {
                        givenChestCoordinates1[i] = blockLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenChestCoordinates1[i] = blockLocation.getBlock().getLocation().getBlockZ();
                    }
                } else {
                    // Cannot use relative coordinates from the console
                    commandSender.sendMessage("You cannot use relative coordinates from the console.");
                    return false;
                }
            } else if (util.stringIsInteger(arg)) {
                // Parse the integer value
                givenChestCoordinates1[i] = Integer.parseInt(arg);
            } else {
                // Invalid coordinate argument
                return false;
            }
        }

        // is args[3 through 5] integers (coordinates)?
        for (int i = 3; i < 6; i++) {
            // Safely get the argument
            String arg = null;
            if (i < args.length) {
                arg = args[i];
            } else {
                return false;
            }

            if (arg.equals("~")) {
                if (commandSender instanceof Player) {
                    // Use the player's current location for relative coordinates
                    Location playerLocation = ((Player) commandSender).getLocation();
                    if (i == 3) {
                        givenChestCoordinates2[i - 3] = playerLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 4) {
                        givenChestCoordinates2[i - 3] = playerLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenChestCoordinates2[i - 3] = playerLocation.getBlock().getLocation().getBlockZ();
                    }

                } else if (commandSender instanceof BlockCommandSender) {
                    // Use the command block's location for relative coordinates
                    Location blockLocation = ((BlockCommandSender) commandSender).getBlock().getLocation();
                    if (i == 3) {
                        givenChestCoordinates2[i - 3] = blockLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 4) {
                        givenChestCoordinates2[i - 3] = blockLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenChestCoordinates2[i - 3] = blockLocation.getBlock().getLocation().getBlockZ();
                    }
                } else {
                    // Cannot use relative coordinates from the console
                    commandSender.sendMessage("You cannot use relative coordinates from the console.");
                    return false;
                }
            } else if (util.stringIsInteger(arg)) {
                // Parse the integer value
                givenChestCoordinates2[i - 3] = Integer.parseInt(arg);
            } else {
                // Invalid coordinate argument
                return false;
            }
        }

        // if args[6] an item?
        if (Material.matchMaterial(args[6]) != null) {
            // The string represents a valid item
            givenItem = Material.matchMaterial(args[6]);
        } else {
            return false;
        }

        // is args[7] an integer (minimum amount)?
        if (util.stringIsInteger(args[7])) {
            givenMinAmount = Integer.parseInt(args[7]);
        } else {
            return false;
        }

        // is args[8] an integer (maximum amount)?
        if (util.stringIsInteger(args[8])) {
            givenMaxAmount = Integer.parseInt(args[8]);
        } else {
            return false;
        }

        // is args[7] (minimum amount) <= args[8] (maximum amount)?
        if (givenMinAmount > givenMaxAmount) {
            return false;
        }

        // is args[7] (minimum amount) >= 0?
        if (givenMinAmount < 0) {
            return false;
        }

        // is args[8] (maximum amount) >= 0?
        //noinspection ConstantValue (why does it say that????)
        if (givenMaxAmount < 0) {
            return false;
        }

        int[] randomChestCoordinates = new int[3];

        for (int i = 0; i < 3; i++) {
            int min = Math.min(givenChestCoordinates1[i], givenChestCoordinates2[i]);
            int max = Math.max(givenChestCoordinates1[i], givenChestCoordinates2[i]);

            randomChestCoordinates[i] = (int) (Math.random() * (max - min + 1)) + min;
        }

        // create chest
        World chestWorld;
        if (commandSender instanceof Player) {
            chestWorld = ((Player) commandSender).getWorld();
        } else if (commandSender instanceof BlockCommandSender) {
            chestWorld = ((BlockCommandSender) commandSender).getBlock().getWorld();
        } else {
            commandSender.sendMessage("This command cannot be executed from the console.");
            return false;
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

        // Cast the block to Chest and get its inventory
        int amountOfItems = 0;
        if (chestBlock.getState() instanceof Chest) {
            Chest chest = (Chest) chestBlock.getState();
            Inventory chestInventory = chest.getInventory();

            // Add items to the chest inventory
            // Create item stack
            amountOfItems = (int) Math.floor(Math.random() * (givenMaxAmount - givenMinAmount + 1) + givenMinAmount);
            assert givenItem != null;
            ItemStack chestInventoryItemStack = new ItemStack(givenItem, amountOfItems);

            // Add the items
            chestInventory.addItem(chestInventoryItemStack);
        }

        String responseMessage = "Created chest at " + randomChestCoordinates[0] + ", " + randomChestCoordinates[1] + ", " + randomChestCoordinates[2] + " with " + amountOfItems + " " + givenItem + ".";
        commandSender.sendMessage(responseMessage);

        return true;
    }
}
