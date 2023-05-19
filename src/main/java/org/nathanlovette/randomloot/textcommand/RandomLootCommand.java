package org.nathanlovette.randomloot.textcommand;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.nathanlovette.randomloot.util;

public class RandomLootCommand implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        int[] givenCoordinates = new int[3];

        Material givenItem;

        int givenMinAmount;

        int givenMaxAmount;

        if (args.length != 6) {
            return false;
        }

        // is args[0 through 2] integers (coordinates)?
        for (int i = 0; i < 3; i++) {
            String arg = args[i];

            if (arg.equals("~")) {
                if (commandSender instanceof Player) {
                    // Use the player's current location for relative coordinates
                    Location playerLocation = ((Player) commandSender).getLocation();
                    if (i == 0) {
                        givenCoordinates[i] = playerLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 1) {
                        givenCoordinates[i] = playerLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenCoordinates[i] = playerLocation.getBlock().getLocation().getBlockZ();
                    }

                } else if (commandSender instanceof BlockCommandSender) {
                    // Use the command block's location for relative coordinates
                    Location blockLocation = ((BlockCommandSender) commandSender).getBlock().getLocation();
                    if (i == 0) {
                        givenCoordinates[i] = blockLocation.getBlock().getLocation().getBlockX();
                    } else if (i == 1) {
                        givenCoordinates[i] = blockLocation.getBlock().getLocation().getBlockY();
                    } else {
                        givenCoordinates[i] = blockLocation.getBlock().getLocation().getBlockZ();
                    }
                } else {
                    // Cannot use relative coordinates from the console
                    commandSender.sendMessage("You cannot use relative coordinates from the console.");
                    return false;
                }
            } else if (util.stringIsInteger(arg)) {
                // Parse the integer value
                givenCoordinates[i] = Integer.parseInt(arg);
            } else {
                // Invalid coordinate argument
                return false;
            }
        }

        // if args[3] an item?
        if (Material.matchMaterial(args[3]) != null) {
            // The string represents a valid item
            givenItem = Material.matchMaterial(args[3]);
        } else {
            return false;
        }

        // is args[4] an integer (minimum amount)?
        if (util.stringIsInteger(args[4])) {
            givenMinAmount = Integer.parseInt(args[4]);
        } else {
            return false;
        }

        // is args[5] an integer (maximum amount)?
        if (util.stringIsInteger(args[5])) {
            givenMaxAmount = Integer.parseInt(args[5]);
        } else {
            return false;
        }

        // is args[4] (minimum amount) <= args[5] (maximum amount)?
        if (givenMinAmount > givenMaxAmount) {
            return false;
        }

        // is args[4] (minimum amount) >= 0?
        if (givenMinAmount < 0) {
            return false;
        }

        // is args[5] (maximum amount) >= 0?
        //noinspection ConstantValue (why does it say that????)
        if (givenMaxAmount < 0 ) {
            return false;
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

        Location chestLocation = new Location(chestWorld, givenCoordinates[0], givenCoordinates[1], givenCoordinates[2]);

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
        if (chestBlock.getState() instanceof Chest) {
            Chest chest = (Chest) chestBlock.getState();
            Inventory chestInventory = chest.getInventory();

            // Add items to the chest inventory
            // Create item stack
            int amountOfItems = (int) Math.floor(Math.random() * (givenMaxAmount - givenMinAmount + 1) + givenMinAmount);
            assert givenItem != null;
            ItemStack chestInventoryItemStack = new ItemStack(givenItem, amountOfItems);

            // Add the items
             chestInventory.addItem(chestInventoryItemStack);
        }

        return true;
    }
}
