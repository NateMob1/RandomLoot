package org.nathanlovette.randomloot.menucommand;

import java.util.ArrayList;

public class RandomLootMenu {
    private final ArrayList<MenuButton> buttons;
    String title;
    int menuSize;

    public RandomLootMenu(String title, int menuSize, ArrayList<MenuButton> buttons) {
        this.title = title;
        this.menuSize = menuSize;
        this.buttons = buttons;
    }
}
