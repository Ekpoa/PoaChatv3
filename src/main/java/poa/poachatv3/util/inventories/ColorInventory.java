package poa.poachatv3.util.inventories;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import poa.poachatv3.util.ChatColors;
import poa.poachatv3.util.inventories.holders.ChatColorHolder;
import poa.poachatv3.util.inventories.holders.ChatColorMainHolder;
import poa.poalib.items.CreateItem;

import java.util.List;
import java.util.Objects;

public class ColorInventory {


    public static Inventory chatColorMainInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(new ChatColorMainHolder(), InventoryType.DROPPER, MiniMessage.miniMessage().deserialize("<rainbow>Chat Color Menu"));

        CreateItem.fillInventory(inventory, CreateItem.blackGlass());

        inventory.setItem(0, CreateItem.createItem(Material.LIME_CANDLE, "<rainbow>Colors", "<gray>Choose your chat color"));
        if (player.hasPermission("poa.chat.color.gradient"))
            inventory.setItem(2, CreateItem.createItem(Material.NETHER_STAR, "<gradient:light_purple:white>Gradient Colors", "<gray>Choose a custom gradient chat color"));
        else
            inventory.setItem(2, CreateItem.createItem(Material.NETHER_STAR, "<gradient:light_purple:white>Gradient Colors", "<gray>Choose a custom gradient chat color", "<red>You are lacking permission to use this"));

        if (player.hasPermission("poa.chat.color.custom"))
            inventory.setItem(1, CreateItem.createItem(Material.COMMAND_BLOCK, "<aqua>Custom Chat Color", "<gray>Choose a custom chat color"));
        else
            inventory.setItem(1, CreateItem.createItem(Material.COMMAND_BLOCK, "<Aqua>Custom Chat Color", "<gray>Choose a custom chat color", "<red>You are lacking permission to use this"));

        if (player.hasPermission("poa.chat.tags"))
            inventory.setItem(4, CreateItem.createItem(Material.NAME_TAG, "<green>Chat Tags", "<gray>View your chat tags"));
        else
            inventory.setItem(4, CreateItem.createItem(Material.NAME_TAG, "<green>Chat Tags", "<gray>View your chat tags", "<red>You are lacking permission to use this"));


        if (player.hasPermission("poa.chat.namecolor"))
            inventory.setItem(6, CreateItem.createItem(Material.ANVIL, "<yellow>Name Color", "<gray>Choose a name chat color"));
        else
            inventory.setItem(6, CreateItem.createItem(Material.ANVIL, "<yellow>Name Color", "<gray>Choose a name chat color", "<red>You are lacking permission to use this"));

        inventory.setItem(8, CreateItem.createItem(Material.BARRIER, "<dark_red>Close"));

        return inventory;
    }


    public static Inventory colorInventory(Player player, int page) {
        final Inventory inventory;

        final ChatColorHolder holder = new ChatColorHolder();
        holder.setPage(page);


        if (page == 0) {
            inventory = Bukkit.createInventory(holder, 54, MiniMessage.miniMessage().deserialize("<rainbow>Chat Colors"));
            CreateItem.fillInventory(inventory, CreateItem.blackGlass());

            int s = 0;
            for (String category : ChatColors.rawColors) {
                for (ChatColors chatColor : Objects.requireNonNull(ChatColors.getChatColors(category))) {
                    inventory.setItem(s, chatColor.getItem(player));
                    s++;
                }
            }
            inventory.setItem(53, CreateItem.createItem(Material.ARROW, "&bNext Page", List.of("&7Click to back a page"), "PoaForward", true));
        } else {
            inventory = Bukkit.createInventory(holder, 54, MiniMessage.miniMessage().deserialize("<rainbow>Chat Colors"));
            CreateItem.fillInventory(inventory, CreateItem.blackGlass());

            int s = -1;
            for (String category : ChatColors.otherColors) {
                for (ChatColors chatColor : Objects.requireNonNull(ChatColors.getChatColors(category))) {
                    s++;
                    inventory.setItem(s, chatColor.getItem(player));
                }
                s += 9;
            }
        }

        inventory.setItem(inventory.getSize() - 9, CreateItem.createItem(Material.ARROW, "&bPrevious Page", List.of("&7Click to back a page"), "PoaBack", true));

        return inventory;
    }

    public static Inventory colorInventory(Player player) {
        return colorInventory(player, 0);
    }

}
