package cc.leafed.tcc_core.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
    public static ItemStack nameItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r" + name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack nameItem(Material mat, String name, String desc) {
        ItemStack item = nameItem(mat, name);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("§r" + desc);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
