package poa.poachatv3.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import poa.poalib.items.CreateItem;
import poa.poalib.messages.Messages;

import java.util.List;

public class TagItems {


    public static ItemStack tagItem(String tag, boolean forInventory){
        tag = Messages.essentialsToMinimessage(tag);

        List<String> lore = List.of("<gray>Click to claim", "<red>Non refundable");
        if(forInventory)
            lore = List.of("<gray>Click to set");

        return CreateItem.createItem(Material.NAME_TAG, tag, lore, "PoaChatTag", tag);
    }



}
