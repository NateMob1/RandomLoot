package org.nathanlovette.randomloot.menucommand;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuButton {
    public Material material;
    public int amount;
    public int slot;
    public Types type;
    public String name;

    public MenuButton(Material material, int amount, int slot, Types type, String name) {
        this.material = material;
        this.amount = amount;
        this.slot = slot;
        this.type = type;
        this.name = name;
    }

    public static enum Types {
        ChestInfo,
        ItemInfo
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}

