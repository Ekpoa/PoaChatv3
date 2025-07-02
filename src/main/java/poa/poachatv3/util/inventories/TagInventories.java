package poa.poachatv3.util.inventories;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import poa.poachatv3.PoaChatv3;
import poa.poachatv3.events.InvClick;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.TagItems;
import poa.poachatv3.util.inventories.holders.TagHolder;
import poa.poalib.items.CreateItem;

import java.util.List;
import java.util.logging.Level;

public class TagInventories {

    public static Inventory tagInventory(PlayerData playerData, int page){
        final TagHolder tagHolder = new TagHolder();
        tagHolder.setPage(0);
        final Inventory inventory = Bukkit.createInventory(tagHolder, 54, MiniMessage.miniMessage().deserialize("<green>Chat Tags " + (page + 1)));

        inventory.setItem(45, CreateItem.createItem(Material.ARROW, "&bPrevious Page", List.of("<gray>Click to go back"), "PoaBack", true));


        final List<String> tags = playerData.getTags();
        if(tags.isEmpty())
            return inventory;

        for (int i = 45; i < 54; i++)
            inventory.setItem(i, CreateItem.blackGlass());



        int index = 45 * page;

        for (int i = 0; i < 45; i++) {
            if(tags.size() <= index)
                break;

            inventory.setItem(i, TagItems.tagItem(tags.get(index), true));
            index++;
        }

        if(tags.size() > index)
            inventory.setItem(53, CreateItem.createItem(Material.ARROW, "&bNext Page", List.of("<gray>Click to go forward"), "PoaForward", true));

        inventory.setItem(49, CreateItem.createItem(Material.JIGSAW, "&6Randomize", List.of("<gray>Choose randomly when you type"), "PoaTagRandom", true));

        return inventory;
    }

    public static Inventory tagInventory(PlayerData playerData){
        return tagInventory(playerData, 0);
    }





}
