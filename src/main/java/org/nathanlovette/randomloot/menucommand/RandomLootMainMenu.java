package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Material;
import org.nathanlovette.randomloot.menucommand.MenuButton;

import java.util.ArrayList;

public class RandomLootMainMenu {
    String title = "Random Loot Menu";
    int menuSize = 9;

    ArrayList<MenuButton> buttons = new ArrayList<>();

    public RandomLootMainMenu() {
        buttons.add(new MenuButton(Material.CHEST, 1, 0, MenuButton.Types.ChestInfo, "Chest Info"));
        buttons.add(new MenuButton(Material.APPLE, 1, 1, MenuButton.Types.ItemInfo, "Item Info"));
    }
}
