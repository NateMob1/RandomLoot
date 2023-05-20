package org.nathanlovette.randomloot.menucommand;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

public class TempPlayerStorage {
    public static Dictionary<UUID, Chest> playerOpenChests = new Hashtable<>();

    public static Dictionary<UUID, LootItem> playerOpenLootItems = new Hashtable<>();
}
