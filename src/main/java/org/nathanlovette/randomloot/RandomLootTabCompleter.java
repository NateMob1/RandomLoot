package org.nathanlovette.randomloot;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RandomLootTabCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Generate suggestions for the xCoordinate argument
            completions.add("~");
        } else if (args.length == 2) {
            // Generate suggestions for the yCoordinate argument
            completions.add("~");
        } else if (args.length == 3) {
            // Generate suggestions for the zCoordinate argument
            completions.add("~");
        } else if (args.length == 4) {
            // Generate suggestions for the item argument
            String input = args[3].toLowerCase(); // Get the input for the item argument
            for (Material material : Material.values()) {
                String materialName = material.name().toLowerCase();
                if (materialName.startsWith(input)) {
                    completions.add(materialName);
                }
            }
        } else if (args.length == 5) {
            // Generate suggestions for the minimumAmountInteger argument
//            completions.add("1");
//            completions.add("5");
        } else if (args.length == 6) {
            // Generate suggestions for the maximumAmountInteger argument
//            completions.add("10");
//            completions.add("20");
        }

        return completions;
    }
}
