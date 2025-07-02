package poa.poachatv3.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import poa.poachatv3.commands.ChatAdmin;
import poa.poachatv3.commands.PrefixCMD;
import poa.poachatv3.util.ChatColors;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.TagItems;
import poa.poachatv3.util.inventories.ColorInventory;
import poa.poachatv3.util.inventories.TagInventories;
import poa.poachatv3.util.inventories.holders.ChatColorHolder;
import poa.poachatv3.util.inventories.holders.ChatColorMainHolder;
import poa.poachatv3.util.inventories.holders.TagHolder;
import poa.poalib.shaded.NBT;

import java.util.*;

public class InvClick implements Listener {

    public static final List<Player> isChoosingGradient = new ArrayList<>(); // cleanup handled in InvClose
    public static final Map<Player, String> firstColorMap = new HashMap<>();

    public static final List<Player> isChoosingNameColor = new ArrayList<>();

    public static final List<Player> isChoosingCustomColor = new ArrayList<>(); // handled in Chat

    public static final List<Player> isChoosingPrefixColor = new ArrayList<>();

    @EventHandler
    public void mainClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder(false) instanceof ChatColorMainHolder))
            return;

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();


        switch (e.getRawSlot()) {
            case 0 -> player.openInventory(ColorInventory.colorInventory(player));
            case 2 -> {
                if (!player.hasPermission("poa.chat.color.gradient"))
                    return;

                isChoosingGradient.add(player);
                final Inventory inventory = ColorInventory.colorInventory(player);
                ((ChatColorHolder) Objects.requireNonNull(inventory.getHolder(false))).setChoosingGradient(true);
                player.openInventory(inventory);
            }
            case 1 -> {
                if (!player.hasPermission("poa.chat.color.custom"))
                    return;

                player.closeInventory();
                player.sendRichMessage("<green>In Chat type a custom hex color only!");
                isChoosingCustomColor.add(player);
            }

            case 4 -> {
                player.openInventory(TagInventories.tagInventory(PlayerData.getPlayerData(player)));
            }

            case 6 -> {
                if (!player.hasPermission("poa.chat.namecolor"))
                    return;

                isChoosingNameColor.add(player);
                final Inventory inventory = ColorInventory.colorInventory(player);
                ((ChatColorHolder) Objects.requireNonNull(inventory.getHolder(false))).setChoosingNameColor(true);
                player.openInventory(inventory);
            }
            case 7 -> {
                if (!player.hasPermission("poa.prefixcolor"))
                    return;

                isChoosingPrefixColor.add(player);
                final Inventory inventory = ColorInventory.colorInventory(player);;
                player.openInventory(inventory);
            }

            case 8 -> player.closeInventory();
        }

    }

    @EventHandler
    public void colorClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder(false) instanceof ChatColorHolder holder))
            return;

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        final ItemStack item = e.getCurrentItem();
        if (item == null || item.isEmpty())
            return;


        final String colorName = NBT.get(item, nbt -> {
            if (nbt.hasTag("PoaForward")) {
                player.openInventory(ColorInventory.colorInventory(player, 1));
                return null;
            } else if (nbt.hasTag("PoaBack")) {
                if (holder.getPage() == 1)
                    player.openInventory(ColorInventory.colorInventory(player, 0));
                else
                    player.openInventory(ColorInventory.chatColorMainInventory(player));
                return null;
            }

            if (!nbt.hasTag("PoaColorName"))
                return null;

            return nbt.getString("PoaColorName");
        });

        if (colorName == null)
            return;

        final ChatColors chatColor = ChatColors.getChatColor(colorName);
        if (chatColor == null)
            return;

        if (!chatColor.hasPermission(player)) {
            final String[] permissions = chatColor.getPermissions();
            player.sendRichMessage("<red>You are missing the permission " + permissions[0] + " or " + permissions[1]);
            return;
        }

        final PlayerData playerData = PlayerData.getPlayerData(player);
        final String color = chatColor.getChatColor();

        if (isChoosingGradient.contains(player)) { // is choosing gradient
            if (!firstColorMap.containsKey(player)) {
                firstColorMap.put(player, color.replaceAll("<", "").replaceAll(">", ""));
                player.sendRichMessage(color + "First color chosen. Choose second color");
                return;
            }

            String newColor = "<gradient:" + firstColorMap.get(player) + ":" + (color.replaceAll("<", "").replaceAll(">", "")) + ">";

            firstColorMap.remove(player);
            playerData.setChatColor(newColor);
            player.sendRichMessage(playerData.getChatColor() + "Your chat color has been set");
        } else if (isChoosingNameColor.contains(player)) {
            playerData.setNameColor(color);
            player.sendRichMessage(playerData.getNameColor() + "Your chat name color has been set");
        } else if (isChoosingPrefixColor.contains(player)) {
            if (!firstColorMap.containsKey(player)) {
                firstColorMap.put(player, color.replaceAll("<", "").replaceAll(">", ""));
                player.sendRichMessage(color + "First color chosen. Choose second color");
                return;
            }

            String newColor = "<gradient:" + firstColorMap.get(player) + ":" + (color.replaceAll("<", "").replaceAll(">", "")) + ">";

            firstColorMap.remove(player);
            playerData.setPrefixColor(newColor);
            PrefixCMD.setPrefixColor(player.getUniqueId(), newColor);

            player.sendRichMessage(newColor + "Your prefix color has been set");
        } else {
            playerData.setChatColor(color);
            player.sendRichMessage(playerData.getChatColor() + "Your chat color has been set");
        }
    }

    @EventHandler
    public void tagClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder(false) instanceof TagHolder holder))
            return;

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        final ItemStack item = e.getCurrentItem();
        if (item == null || item.isEmpty())
            return;

        PlayerData playerData = PlayerData.getPlayerData(player);

        if (ChatAdmin.isRemovingTag.containsKey(player))
            playerData = ChatAdmin.isRemovingTag.get(player);

        if (e.getClickedInventory() != e.getView().getTopInventory())
            return;

        PlayerData finalPlayerData = playerData;
        NBT.get(item, nbt -> {
            final int page = holder.getPage();
            if (nbt.hasTag("PoaBack")) {
                if (page == 0)
                    player.openInventory(ColorInventory.chatColorMainInventory(player));
                else
                    player.openInventory(TagInventories.tagInventory(finalPlayerData, page - 1));
                return;
            } else if (nbt.hasTag("PoaForward")) {
                player.openInventory(TagInventories.tagInventory(finalPlayerData, page + 1));
                return;
            } else if (nbt.hasTag("PoaTagRandom")) {
                finalPlayerData.setCurrentTag("££RANDOM££");
                player.closeInventory();
                if (ChatAdmin.isRemovingTag.containsKey(player)) {
                    player.sendRichMessage("<green>Random tags will be used when this user types");
                    return;
                }
                player.sendRichMessage("<green>Random tags will be used when you type");
                return;
            }

            if (!nbt.hasTag("PoaChatTag"))
                return;

            final String tag = nbt.getString("PoaChatTag");

            if (ChatAdmin.isRemovingTag.containsKey(player)) {
                finalPlayerData.removeTag(tag);
                player.openInventory(TagInventories.tagInventory(finalPlayerData, page));
                player.getInventory().addItem(TagItems.tagItem(tag, false));
                return;
            }

            finalPlayerData.setCurrentTag(tag);
            player.closeInventory();
            player.sendRichMessage("<green>Set current tag to " + tag);
        });

    }

}
