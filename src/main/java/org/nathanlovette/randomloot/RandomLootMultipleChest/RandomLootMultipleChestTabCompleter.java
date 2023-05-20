package org.nathanlovette.randomloot.RandomLootMultipleChest;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RandomLootMultipleChestTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Generate suggestions for the first xCoordinate argument
            completions.add("~");
        } else if (args.length == 2) {
            // Generate suggestions for the first yCoordinate argument
            completions.add("~");
        } else if (args.length == 3) {
            // Generate suggestions for the first zCoordinate argument
            completions.add("~");
        } else if (args.length == 4) {
            // Generate suggestions for the second xCoordinate argument
            completions.add("~");
        } else if (args.length == 5) {
            // Generate suggestions for the second yCoordinate argument
            completions.add("~");
        } else if (args.length == 6) {
            // Generate suggestions for the second zCoordinate argument
            completions.add("~");
        } else if (args.length >= 7 && args.length % 3 == 1) {
            // Generate suggestions for item arguments starting from the seventh argument
            String input = args[args.length - 1].toLowerCase(); // Get the input for the item argument
            for (Material material : Material.values()) {
                String materialName = material.name().toLowerCase();
                if (materialName.startsWith(input)) {
                    completions.add(materialName);
                }
            }
        } else if (args.length >= 8 && args.length % 3 == 2) {
            // Generate suggestions for minAmount arguments
//            completions.add("<minAmount>");
        } else if (args.length >= 9 && args.length % 3 == 0) {
            // Generate suggestions for maxAmount arguments
//            completions.add("<maxAmount>");
        }

        return completions;
    }
}
