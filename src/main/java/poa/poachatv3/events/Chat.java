package poa.poachatv3.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import poa.poachatv3.PoaChatv3;
import poa.poachatv3.util.ChatColors;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.inventories.holders.ChatHolder;
import poa.poalib.economy.Economy;
import poa.poalib.items.ItemNullCheck;
import poa.poalib.messages.Messages;
import poa.poalib.shaded.NBT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat implements Listener {

    private static final Map<UUID, Inventory> inventoryChatMap = new HashMap<>();


    private static Object vaultManagerInstance;
    private static Method loadVaultMethod;

    static {
        try {
            if (PoaChatv3.INSTANCE.getConfig().getBoolean("UsePlayerVaults")) {
                Class<?> vaultManagerClass = Class.forName("com.drtshock.playervaults.vaultmanagement.VaultManager");
                Method getVaultManagerInstance = vaultManagerClass.getDeclaredMethod("getInstance");
                vaultManagerInstance = getVaultManagerInstance.invoke(vaultManagerClass);

                loadVaultMethod = vaultManagerClass.getDeclaredMethod("loadOtherVault", String.class, int.class, int.class);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if (e.isCancelled())
            return;

        final Component componentMessage = e.message();
        final String rawMessage = PlainTextComponentSerializer.plainText().serialize(componentMessage);
        final Player player = e.getPlayer();
        final PlayerData playerData = PlayerData.getPlayerData(player);
        final String chatColor = playerData.getChatColor();
        final String nameColor = playerData.getNameColor();

        final String stringDisplayName = playerData.getDisplayName();

        String tag = playerData.getCurrentTag();

        if (tag.equalsIgnoreCase("££RANDOM££")) {
            final List<String> tags = playerData.getTags();
            if (tags.isEmpty()) {
                tag = "";
                playerData.setCurrentTag("");
            } else
                tag = tags.get(ThreadLocalRandom.current().nextInt(0, tags.size()));
        }

        if (!tag.isEmpty()) {
            tag = " " + tag;
        }
        final Component displayname = MiniMessage.miniMessage().deserialize(nameColor + stringDisplayName + tag);


        //start of chat modules

        if (rawMessage.contains("[help]")) {
            if (player.hasPermission("poa.chat.modules.help")) {
                e.setCancelled(true);
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gold><b>All possible chat modules"));
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[help] <yellow>Shows this... duh"));
                if (player.hasPermission("poa.chat.modules.item"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[item] <yellow>Shows ur item"));
                if (player.hasPermission("poa.chat.modules.kills"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[kills] <yellow>Shows ur kills"));
                if (player.hasPermission("poa.chat.modules.deaths"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[deaths] <yellow>Shows ur deaths"));
                if (player.hasPermission("poa.chat.modules.kdr"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[kdr] <yellow>Shows ur kill death ratio"));
                if (player.hasPermission("poa.chat.modules.playtime"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[playtime] <yellow>Shows ur playtime"));
                if (player.hasPermission("poa.chat.modules.inventory"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[inventory] <yellow>Shows ur inventory"));
                if (player.hasPermission("poa.chat.modules.enderchest"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[enderchest] <yellow>Shows ur enderchest"));
                if (player.hasPermission("poa.chat.modules.timesincedeath"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[time since death] <yellow>Shows ur time since death"));
                if (player.hasPermission("poa.chat.modules.balance"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[balance] <yellow>Shows ur balance"));
                if (player.hasPermission("poa.chat.modules.titles"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[titles] <yellow>Shows ur titles"));
                if (player.hasPermission("poa.chat.modules.coords"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[coords] <yellow>Shows ur coordinates"));
                if (player.hasPermission("poa.chat.modules.statistic"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[statistic <statistic>] <yellow>Shows the statistic\n<gray>[statistic jumps]"));
                if (player.hasPermission("poa.chat.modules.killed"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[killed <entity>] <yellow>Shows the amount u killed mobs\n<gray>[killed zombie]"));
                if (player.hasPermission("poa.chat.modules.killedby"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[killedby <entity>] <yellow>Shows the amount u times a mob killed u\n<gray>[killedby zombie]"));
                if (player.hasPermission("poa.chat.modules.placed"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[placed <material>] <yellow>Shows the amount a block you placed\n<gray>[placed dirt]"));
                if (player.hasPermission("poa.chat.modules.mined"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[mined <material>] <yellow>Shows the amount a block you mined\n<gray>[mined stone]\n<gray>[mined all]"));
                if (player.hasPermission("poa.chat.modules.pv"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[pv <number>] <yellow>Shows your private vault"));
                if (player.hasPermission("poa.chat.modules.level"))
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>[level] <yellow>Shows level"));

                player.sendMessage("");
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>:skull: <yellow>\u2620"));
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[item]")) {
            if (player.hasPermission("poa.chat.modules.item")) {
                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType().isAir()) {
                    player.sendRichMessage("<red>Hold an item");
                    e.setCancelled(true);
                    return;
                }


                String name;


                ItemMeta meta = item.getItemMeta();
                if (meta.displayName() != null)
                    name = MiniMessage.miniMessage().serialize(meta.displayName());
                else
                    name = item.getType().toString();


                int a = 0;
                for (ItemStack i : player.getInventory()) {
                    if (ItemNullCheck.isNull(i))
                        continue;
                    if (i.isSimilar(item))
                        a = a + i.getAmount();
                }

                Component m = messageSplit("item", "<dark_gray>[<gray>" + a + "x <aqua>" + name + "<dark_gray><b:false><i:false><u:false><obf:false>]", rawMessage, playerData);
                m = m.hoverEvent(item);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[kills]")) {
            if (player.hasPermission("poa.chat.modules.kills")) {
                Component m = messageSplit("kills", "<dark_gray>[<gold>Kills: " + player.getStatistic(Statistic.PLAYER_KILLS) + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[deaths]")) {
            if (player.hasPermission("poa.chat.modules.deaths")) {
                Component m = messageSplit("deaths", "<dark_gray>[<gold>Deaths: " + player.getStatistic(Statistic.DEATHS) + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[kdr]")) {
            if (player.hasPermission("poa.chat.modules.kdr")) {
                double k = player.getStatistic(Statistic.PLAYER_KILLS);
                double d = player.getStatistic(Statistic.DEATHS);
                double kdr = k / d;

                if (Double.isNaN(kdr))
                    kdr = 0;

                Component m = messageSplit("kdr", String.format("<dark_gray>[<gold>KDR: %.2f<dark_gray>]", kdr), rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[placed]")) {
            if (player.hasPermission("poa.chat.modules.placed")) {
                int x = 0;
                for (Material m : Material.values()) {
                    try {
                        x = x + player.getStatistic(Statistic.USE_ITEM, m);
                    } catch (Exception ignored) {
                    }
                }

                Component m = messageSplit("placed", "<dark_gray>[<gold>Placed: " + x + " Blocks<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[playtime]")) {
            if (player.hasPermission("poa.chat.modules.playtime")) {
                Component m = messageSplit("playtime", "<dark_gray>[<yellow>Playtime: " + Messages.timeToStringShort(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[time since death]")) {
            if (player.hasPermission("poa.chat.modules.timesincedeath")) {
                Component m = messageSplit("time since death", "<dark_gray>[<red>Time Since Death: " + Messages.timeToStringShort(player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20) + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[ping]")) {
            if (player.hasPermission("poa.chat.modules.ping")) {

                Component m = messageSplit("ping", "<dark_gray>[<green>Ping: " + player.getPing() + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[balance]")) {
            if (player.hasPermission("poa.chat.modules.balance")) {
                Component m = messageSplit("balance", "<dark_gray>[<green>Balance: " + Messages.numberFormat(Economy.getBalance(player)) + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[coords]")) {
            if (player.hasPermission("poa.chat.modules.coords")) {
                Location location = player.getLocation();
                Component m = messageSplit("coords", "<dark_gray>[<aqua>" + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + " in " + location.getWorld().getName() + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[inventory]")) {
            if (player.hasPermission("poa.chat.modules.inventory")) {
                UUID u = UUID.randomUUID();

                Inventory inv = Bukkit.createInventory(new ChatHolder(), 45, MiniMessage.miniMessage().deserialize("<gold>" + player.getName()));

                for (int i = 0; i < 36; i++) {
                    inv.setItem(i, player.getInventory().getItem(i));
                }

                inv.setItem(36, player.getEquipment().getHelmet());
                inv.setItem(37, player.getEquipment().getChestplate());
                inv.setItem(38, player.getEquipment().getLeggings());
                inv.setItem(39, player.getEquipment().getBoots());
                inv.setItem(40, player.getEquipment().getItemInOffHand());

                inventoryMap.put(u, inv);

                Bukkit.getScheduler().runTaskLater(PoaChatv3.INSTANCE, () -> inventoryMap.remove(u), 6000);

                Component m = messageSplit("inventory", "<dark_gray><click:run_command:/secretchat " + u + ">[<gold>" + player.getName() + "'s Inventory<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[enderchest]")) {
            if (player.hasPermission("poa.chat.modules.enderchest")) {


                UUID u = UUID.randomUUID();

                Inventory inv = Bukkit.createInventory(new ChatHolder(), 54, MiniMessage.miniMessage().deserialize("<gold>" + player.getName()));

                for (int i = 0; i < 54; i++)
                    inv.setItem(i, player.getEnderChest().getItem(i));

                inventoryMap.put(u, inv);

                Bukkit.getScheduler().runTaskLater(PoaChatv3.INSTANCE, () -> inventoryMap.remove(u), 6000);

                Component m = messageSplit("enderchest", "<dark_gray><click:run_command:/secretchat " + u + ">[<dark_purple>" + player.getName() + "'s Enderchest<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[statistic ")) {
            if (player.hasPermission("poa.chat.modules.statistic")) {
                String statString = getArg("statistic", rawMessage);
                try {
                    Statistic statistic = Statistic.valueOf(statString.toUpperCase());
                    int value = player.getStatistic(statistic);
                    Component m = messageSplit("statistic " + statString, "<dark_gray>[<yellow>Statistic: " + statString.replace("_", " ") + ": " + value + "<dark_gray>]", rawMessage, playerData);
                    e.message(m);
                } catch (Exception ignored) {
                    e.setCancelled(true);
                    player.sendRichMessage("<red><i>" + statString + "<i:false> is not a valid statistic, use <click:open_url:https://jd.papermc.io/paper/1.20/org/bukkit/Statistic.html><yellow><i> any of these <gray>(click)");
                    return;
                }
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[killed ")) {
            if (player.hasPermission("poa.chat.modules.kill")) {
                String statString = getArg("killed", rawMessage);
                try {
                    EntityType entityType = EntityType.valueOf(statString.toUpperCase());
                    int value = player.getStatistic(Statistic.KILL_ENTITY, entityType);
                    Component m = messageSplit("killed " + statString, "<dark_gray>[<yellow>Killed " + statString.replace("_", " ") + ": " + value + "<dark_gray>]", rawMessage, playerData);
                    e.message(m);
                } catch (Exception ignored) {
                    e.setCancelled(true);
                    player.sendRichMessage("<red><i>" + statString + "<i:false> is not a valid entity, use <click:open_url:https://jd.papermc.io/paper/1.20/org/bukkit/entity/EntityType.html><yellow><i> any of these <gray>(click)");
                    return;
                }

            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[killedby ")) {
            if (player.hasPermission("poa.chat.modules.killedby")) {
                String statString = getArg("killedby", rawMessage);
                try {
                    EntityType entityType = EntityType.valueOf(statString.toUpperCase());
                    int value = player.getStatistic(Statistic.KILL_ENTITY, entityType);
                    Component m = messageSplit("killedby " + statString, "<dark_gray>[<yellow>Killed By " + statString.replace("_", " ") + ": " + value + "<dark_gray>]", rawMessage, playerData);
                    e.message(m);
                } catch (Exception ignored) {
                    e.setCancelled(true);
                    player.sendRichMessage("<red><i>" + statString + "<i:false> is not a valid entity, use <click:open_url:https://jd.papermc.io/paper/1.20/org/bukkit/entity/EntityType.html><yellow><i> any of these <gray>(click)");
                    return;
                }

            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[placed ")) {
            if (player.hasPermission("poa.chat.modules.placed")) {
                String statString = getArg("placed", rawMessage);
                try {
                    Material material = Material.valueOf(statString.toUpperCase());
                    int value = player.getStatistic(Statistic.USE_ITEM, material);
                    Component m = messageSplit("placed " + statString, "<dark_gray>[<yellow>Placed " + statString.replace("_", " ") + ": " + value + "<dark_gray>]", rawMessage, playerData);
                    e.message(m);
                } catch (Exception ignored) {
                    e.setCancelled(true);
                    player.sendRichMessage("<red><i>" + statString + "<i:false> is not a valid material, use <click:open_url:https://jd.papermc.io/paper/1.20/org/bukkit/material/package-summary.html><yellow><i> any of these <gray>(click)");
                    return;
                }

            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[mined ")) {
            if (player.hasPermission("poa.chat.modules.placed")) {
                String statString = getArg("mined", rawMessage);
                try {
                    int value = 0;
                    if(statString.equalsIgnoreCase("all")){
                        for (Material material : Material.values()) {
                            value = value + player.getStatistic(Statistic.MINE_BLOCK, material);
                        }
                    }
                    else {
                        Material material = Material.valueOf(statString.toUpperCase());
                        value = player.getStatistic(Statistic.MINE_BLOCK, material);
                    }
                    Component m = messageSplit("mined " + statString, "<dark_gray>[<yellow>Mined " + statString.replace("_", " ") + ": " + value + "<dark_gray>]", rawMessage, playerData);
                    e.message(m);
                } catch (Exception ignored) {
                    e.setCancelled(true);
                    player.sendRichMessage("<red><i>" + statString + "<i:false> is not a valid material, use <click:open_url:https://jd.papermc.io/paper/1.20/org/bukkit/material/package-summary.html><yellow><i> any of these <gray>(click)");
                    return;
                }

            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));
        } else if (rawMessage.contains("[pv ")) {
            if (player.hasPermission("poa.chat.modules.pv")) {
                String pv = getArg("pv", rawMessage);
                if (pv == null) {
                    player.sendRichMessage("<red>not a valid number");
                    e.setCancelled(true);
                    return;
                }
                int vault = 1;
                try {
                    vault = Integer.parseInt(pv);
                } catch (Exception ignored) {
                    player.sendRichMessage("<red>not a valid number");
                    e.setCancelled(true);
                    return;
                }


                Inventory inv = getPrivateVaultClone(player, vault);

                UUID u = UUID.randomUUID();

                inventoryMap.put(u, inv);

                Bukkit.getScheduler().runTaskLater(PoaChatv3.INSTANCE, () -> inventoryMap.remove(u), 6000);

                Component m = messageSplit("pv " + pv, "<dark_gray><click:run_command:/secretchat " + u + ">[<red>" + player.getName() + "'s Private Vault " + pv + "<dark_gray>]", rawMessage, playerData);
                e.message(m);
            } else
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to use this module"));

        }
        else // if no modules are used
            e.message(MiniMessage.miniMessage().deserialize(chatColor + "<message>", Placeholder.unparsed("message", rawMessage)));



        e.renderer((sender, displayName, inputMessage, viewingPlayer) ->
                Component.join(JoinConfiguration.separator(Component.text(" ")), displayname, e.message()));

    }






    private static Component messageSplit(String split, String replace, String rawMessage, PlayerData playerData) {
        List<String> splitted = List.of(rawMessage.split("\\[" + split + "]"));

        String s = "";
        String e = "";
        if (!splitted.isEmpty())
            s = splitted.get(0);
        if (splitted.size() > 1)
            e = splitted.get(1);

        String color = playerData.getChatColor();

        return MiniMessage.miniMessage().deserialize(color + "<s>" + replace + "<b:false><i:false><obf:false><underlined:false>" + color + "<e>",
                Placeholder.unparsed("s", s),
                Placeholder.unparsed("e", e));
    }


    private static String getArg(String startOfSplit, String rawMessage) {
        String regex = "\\[" + startOfSplit + "\\s+(\\S+)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawMessage);
        try {
            matcher.find();
            return matcher.group(1);
        } catch (Exception ignored) {

        }
        return null;
    }


    private static final Map<UUID, Inventory> inventoryMap = new HashMap<>();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        if (!message.startsWith("/secretchat"))
            return;

        e.setCancelled(true);

        List<String> split = List.of(message.split(" "));
        if (split.size() > 1) {
            UUID uuid = UUID.fromString(split.get(1));

            if (inventoryMap.containsKey(uuid))
                e.getPlayer().openInventory(inventoryMap.get(uuid));
        }
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder(false);
        if (holder instanceof ChatHolder)
            e.setCancelled(true);
    }

    @SneakyThrows
    public static Inventory getPrivateVaultClone(OfflinePlayer target, int pv) {
        Inventory vault = (Inventory) loadVaultMethod.invoke(vaultManagerInstance, target.getUniqueId().toString(), pv, 54);

        Inventory inventory = Bukkit.createInventory(new ChatHolder(), 54, MiniMessage.miniMessage().deserialize("<red>" + target.getName() + "'s PV " + pv));

        for (int i = 0; i < 54; i++) {
            ItemStack item = vault.getItem(i);
            if (item == null || item.getType().isAir() || item.isEmpty())
                continue;

            inventory.setItem(i, item.clone());
        }

        return inventory;
    }








    // custom color handling

    @EventHandler(priority = EventPriority.LOW)
    public void customChatColor(AsyncChatEvent e) {
        final Player player = e.getPlayer();
        if (e.isCancelled() || !InvClick.isChoosingCustomColor.contains(player))
            return;

        e.setCancelled(true);

        final Component componentMessage = e.message();
        String rawMessage = PlainTextComponentSerializer.plainText().serialize(componentMessage);
        final PlayerData playerData = PlayerData.getPlayerData(player);

        if (!rawMessage.contains("#"))
            rawMessage = "#" + rawMessage;

        if (!isValidHexColor(rawMessage)) {
            player.sendRichMessage("<red>You must input a hex color for custom chat color. Exiting...");
            InvClick.isChoosingCustomColor.remove(player);
            return;
        }

        playerData.setChatColor("<" + rawMessage + ">");
        player.sendRichMessage(playerData.getChatColor() + "Custom chat color set");
        InvClick.isChoosingCustomColor.remove(player);
    }

    private static boolean isValidHexColor(String input) {
        if (input == null || input.length() != 7 || input.charAt(0) != '#') {
            return false;
        }

        String hexPart = input.substring(1);
        return hexPart.matches("[0-9a-fA-F]{6}");
    }


}
