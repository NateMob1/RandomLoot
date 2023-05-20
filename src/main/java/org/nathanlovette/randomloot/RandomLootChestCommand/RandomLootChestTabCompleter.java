package org.nathanlovette.randomloot.RandomLootChestCommand;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RandomLootChestTabCompleter implements TabCompleter {

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
        } else if (args.length == 7) {
            // Generate suggestions for the item argument
            String input = args[args.length - 1].toLowerCase(); // Get the input for the item argument
            for (Material material : Material.values()) {
                String materialName = material.name().toLowerCase();
                if (materialName.startsWith(input)) {
                    completions.add(materialName);
                }
            }
        }

        return completions;
    }
}
