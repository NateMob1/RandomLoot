package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Material;

import java.util.ArrayList;

public class RandomLootMainMenu extends RandomLootMenu {
    String title;
    int menuSize;

    ArrayList<MenuButton> buttons = new ArrayList<>();

    public RandomLootMainMenu(String title, int menuSize) {
        super(title, menuSize, buttons);

        buttons.add(new MenuButton(Material.CHEST, 1, 0, MenuButton.Types.ChestInfo, "Chest Info"));
        buttons.add(new MenuButton(Material.APPLE, 1, 1, MenuButton.Types.ItemInfo, "Item Info"));
    }
}
