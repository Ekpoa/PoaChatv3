package poa.poachatv3.util;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import poa.poalib.items.CreateItem;
import poa.poalib.shaded.NBT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ChatColors {
    private static final Map<String, List<ChatColors>> dataMap = new HashMap<>();
    private static final Map<String, ChatColors> nameToChatColor = new HashMap<>();
    public static final List<String> rawColors = List.of("BLUE", "GREEN", "RED", "PURPLE", "WHITE");
    public static final List<String> otherColors = List.of("PASTEL", "NEON", "DARK");

    static {
        new ChatColors("BLUE", "Midnight Depths", "<dark_blue>", Material.BLUE_CONCRETE);
        new ChatColors("BLUE", "Stormy Sea", "<#003366>", Material.BLUE_CONCRETE_POWDER);
        new ChatColors("BLUE", "Royal Crest", "<blue>", Material.LAPIS_BLOCK);
        new ChatColors("BLUE", "Sapphire Shine", "<#3366CC>", Material.BLUE_WOOL);
        new ChatColors("BLUE", "Cerulean Dream", "<#3399FF>", Material.LIGHT_BLUE_CONCRETE);
        new ChatColors("BLUE", "Glacial Mist", "<#66CCFF>", Material.LIGHT_BLUE_CONCRETE_POWDER);
        new ChatColors("BLUE", "Crystal Bay", "<aqua>", Material.LIGHT_BLUE_WOOL);
        new ChatColors("BLUE", "Tropical Ice", "<#99FFFF>", Material.LIGHT_BLUE_STAINED_GLASS);
        new ChatColors("BLUE", "Ethereal Glow", "<#CCFFFF>", Material.DIAMOND_BLOCK);

        new ChatColors("GREEN", "Forest Shadow", "<dark_green>", Material.GREEN_CONCRETE);
        new ChatColors("GREEN", "Deep Grove", "<#005500>", Material.GREEN_CONCRETE_POWDER);
        new ChatColors("GREEN", "Verdant Crest", "<green>", Material.GREEN_WOOL);
        new ChatColors("GREEN", "Emerald Veil", "<#33AA33>", Material.LIME_CONCRETE);
        new ChatColors("GREEN", "Spring Bloom", "<#55DD55>", Material.LIME_CONCRETE_POWDER);
        new ChatColors("GREEN", "Mint Leaf", "<#77FF77>", Material.LIME_WOOL);
        new ChatColors("GREEN", "Lime Burst", "<#99FF99>", Material.LIME_STAINED_GLASS);
        new ChatColors("GREEN", "Meadow Shine", "<#BBFFBB>", Material.GREEN_STAINED_GLASS);
        new ChatColors("GREEN", "Frosted Fern", "<#DDFFDD>", Material.SLIME_BLOCK);

        new ChatColors("RED", "Crimson Depths", "<dark_red>", Material.RED_CONCRETE);
        new ChatColors("RED", "Molten Ember", "<#800000>", Material.RED_CONCRETE_POWDER);
        new ChatColors("RED", "Ruby Blaze", "<red>", Material.RED_WOOL);
        new ChatColors("RED", "Flare Burst", "<#FF3300>", Material.RED_STAINED_GLASS);
        new ChatColors("RED", "Searing Flame", "<#FF6600>", Material.ORANGE_CONCRETE);
        new ChatColors("RED", "Amber Glow", "<gold>", Material.ORANGE_CONCRETE_POWDER);
        new ChatColors("RED", "Golden Rise", "<#FFCC00>", Material.GOLD_BLOCK);
        new ChatColors("RED", "Sunfire Spark", "<yellow>", Material.YELLOW_CONCRETE);
        new ChatColors("RED", "Lemon Gleam", "<#FFFFAA>", Material.YELLOW_WOOL);

        new ChatColors("PURPLE", "Abyssal Bloom", "<dark_purple>", Material.PURPLE_CONCRETE);
        new ChatColors("PURPLE", "Royal Hex", "<#4B0082>", Material.PURPLE_CONCRETE_POWDER);
        new ChatColors("PURPLE", "Enchanted Arc", "<light_purple>", Material.PURPLE_WOOL);
        new ChatColors("PURPLE", "Mystic Orchid", "<#CC66FF>", Material.MAGENTA_CONCRETE);
        new ChatColors("PURPLE", "Violet Rush", "<#DD88FF>", Material.MAGENTA_CONCRETE_POWDER);
        new ChatColors("PURPLE", "Roseate Flare", "<#FF99DD>", Material.MAGENTA_WOOL);
        new ChatColors("PURPLE", "Bubblegum Charm", "<#FFBBDD>", Material.PINK_CONCRETE);
        new ChatColors("PURPLE", "Blush Spark", "<#FFCCEE>", Material.PINK_CONCRETE_POWDER);
        new ChatColors("PURPLE", "Sugar Silk", "<#FFEEF5>", Material.PINK_WOOL);

        new ChatColors("WHITE", "Obsidian Mist", "<#202020>", Material.BLACK_CONCRETE);
        new ChatColors("WHITE", "Charcoal Dust", "<gray>", Material.GRAY_CONCRETE);
        new ChatColors("WHITE", "Ashen Veil", "<#555555>", Material.GRAY_CONCRETE_POWDER);
        new ChatColors("WHITE", "Fog Whisper", "<dark_gray>", Material.GRAY_WOOL);
        new ChatColors("WHITE", "Silver Thread", "<#AAAAAA>", Material.LIGHT_GRAY_CONCRETE);
        new ChatColors("WHITE", "Pearl Light", "<#DDDDDD>", Material.LIGHT_GRAY_CONCRETE_POWDER);
        new ChatColors("WHITE", "Snow Drift", "<white>", Material.LIGHT_GRAY_WOOL);
        new ChatColors("WHITE", "Frost Feather", "<#F5F5F5>", Material.WHITE_CONCRETE);
        new ChatColors("WHITE", "Ethereal White", "<#FFFFFF>", Material.WHITE_WOOL);



        new ChatColors("PASTEL", "Pastel Sky", "<#AEC6CF>", Material.LIGHT_BLUE_WOOL);
        new ChatColors("PASTEL", "Lilac Breeze", "<#C8A2C8>", Material.MAGENTA_WOOL);
        new ChatColors("PASTEL", "Peach Fuzz", "<#FFDAB9>", Material.ORANGE_WOOL);
        new ChatColors("PASTEL", "Mint Cream", "<#AAF0D1>", Material.GREEN_WOOL);
        new ChatColors("PASTEL", "Rose Petal", "<#FFB6C1>", Material.PINK_WOOL);
        new ChatColors("PASTEL", "Lemon Frost", "<#FFFFCC>", Material.YELLOW_STAINED_GLASS);
        new ChatColors("PASTEL", "Cotton Candy", "<#FFCCE5>", Material.PINK_STAINED_GLASS);
        new ChatColors("PASTEL", "Sky Foam", "<#D6F5FF>", Material.LIGHT_BLUE_STAINED_GLASS);
        new ChatColors("PASTEL", "Lavender Haze", "<#E6E6FA>", Material.PURPLE_STAINED_GLASS);

        new ChatColors("NEON", "Hot Pink", "<#ff008c>", Material.PINK_CONCRETE);
        new ChatColors("NEON", "Electric Lime", "<#CCFF00>", Material.LIME_CONCRETE);
        new ChatColors("NEON", "Toxic Slime", "<#39FF14>", Material.LIME_STAINED_GLASS);
        new ChatColors("NEON", "Laser Red", "<#FF0033>", Material.RED_CONCRETE);
        new ChatColors("NEON", "Neon Orange", "<#FF6600>", Material.ORANGE_CONCRETE_POWDER);
        new ChatColors("NEON", "Cyber Blue", "<#00FFFF>", Material.LIGHT_BLUE_WOOL);
        new ChatColors("NEON", "Magenta Pulse", "<#FF00FF>", Material.MAGENTA_WOOL);
        new ChatColors("NEON", "Lava Glow", "<#FF4500>", Material.RED_CONCRETE_POWDER);
        new ChatColors("NEON", "Ultra Violet", "<#8F00FF>", Material.PURPLE_CONCRETE);

        new ChatColors("DARK", "Shadow Teal", "<#01473B>", Material.GREEN_CONCRETE);
        new ChatColors("DARK", "Iron Grey", "<#2B2B2B>", Material.GRAY_CONCRETE);
        new ChatColors("DARK", "Coal Dust", "<#1C1C1C>", Material.BLACK_CONCRETE);
        new ChatColors("DARK", "Midnight Plum", "<#2C003E>", Material.PURPLE_CONCRETE_POWDER);
        new ChatColors("DARK", "Ashfall", "<#3E3E3E>", Material.GRAY_CONCRETE_POWDER);
        new ChatColors("DARK", "Dried Earth", "<#5A3E36>", Material.BROWN_CONCRETE);
        new ChatColors("DARK", "Swamp Fog", "<#3C5B43>", Material.GREEN_CONCRETE_POWDER);
        new ChatColors("DARK", "Slate Storm", "<#4A4A4A>", Material.LIGHT_GRAY_WOOL);
        new ChatColors("DARK", "Burnt Clay", "<#8B4513>", Material.BROWN_WOOL);
    }

    public static List<ChatColors> getChatColors(String category) {
        if (dataMap.containsKey(category.toUpperCase()))
            return dataMap.get(category.toUpperCase());

        return null;
    }

    public static ChatColors getChatColor(String name) {
        if (nameToChatColor.containsKey(name.toUpperCase()))
            return nameToChatColor.get(name.toUpperCase());

        return null;
    }

    public static List<String> allCategories(){
        final ArrayList<String> categories = new ArrayList<>(rawColors);
        categories.addAll(otherColors);
        return categories;
    }



    String chatColor;
    String colorName;
    String category;
    Material material;

    public ChatColors(String category, String name, String color, Material material) {
        this.chatColor = color;
        this.colorName = name;
        this.category = category;
        this.material = material;


        List<ChatColors> list = new ArrayList<>();
        if (dataMap.containsKey(category))
            list = dataMap.get(category);

        list.add(this);

        dataMap.put(category, list);
        nameToChatColor.put(colorName.toUpperCase(), this);
    }

    public ItemStack getItem(Player player) {
        ItemStack item = CreateItem.createItem(material, chatColor + colorName, "<gray>Click to set as chat color");
        if (!hasPermission(player))
            item = CreateItem.createItem(material, chatColor + colorName, "<red>You do not have permission to use this color");

        NBT.modify(item, nbt -> {
            nbt.setString("PoaColorName", colorName);
        });

        return item;
    }


    public boolean hasPermission(Player player) {
        final String[] permissions = getPermissions();
        return player.hasPermission(permissions[0]) || player.hasPermission(permissions[1]) || player.hasPermission(permissions[2]);
    }

    public String[] getPermissions() {
        return new String[]{"poa.chat.color." + (colorName.toLowerCase().replaceAll(" ", "_")), "poa.chat.color.all." + category.toLowerCase(), "poa.chat.color.all.all"};
    }

}
