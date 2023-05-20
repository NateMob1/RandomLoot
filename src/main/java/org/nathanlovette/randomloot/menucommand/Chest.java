package org.nathanlovette.randomloot.menucommand;

import java.util.ArrayList;

public class Chest {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public ArrayList<LootItem> items;

    public Chest(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, ArrayList<LootItem> items) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.items = items;
    }

    public Chest() {
        this.items = new ArrayList<>();
    }
}