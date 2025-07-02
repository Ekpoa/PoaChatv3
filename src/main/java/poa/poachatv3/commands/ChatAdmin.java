package poa.poachatv3.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.TagItems;
import poa.poachatv3.util.inventories.TagInventories;
import poa.poalib.messages.Messages;
import poa.poalib.tabcomplete.EasyTabComplete;

import java.util.*;

public class ChatAdmin implements CommandExecutor, TabCompleter {
    public static Map<Player,PlayerData> isRemovingTag = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 2){
            sender.sendRichMessage("<red>/chatadmin <player> <newtagitem/setcurrenttag/addtag/removetag/setchatcolor/setnamecolor/displayname>");
            return false;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        final PlayerData targetData = PlayerData.getPlayerData(target.getUniqueId());

        if(!target.isOnline() && !target.hasPlayedBefore()){
            sender.sendRichMessage("<red>Target not found");
            return false;
        }

        switch (args[1].toLowerCase()){
            case "newtagitem" -> {
                if(args.length < 4) {
                    sender.sendRichMessage("<red>/chatadmin <player> newtagitem [<addbracket = true>] <tag>\n<gray>(the add bracket option is optional)");
                    return false;
                }
                if(!target.isOnline()){
                    sender.sendRichMessage("<red>This command can only be executed against online players");
                    return false;
                }

                final String tag = getTagFromArgs(args, 2);

                ((Player) target).getInventory().addItem(TagItems.tagItem(tag, false));
                sender.sendRichMessage("<green>Added tag to target's inventory");
            }
            case "viewtags" -> {
                if(!(sender instanceof Player player)){
                    sender.sendRichMessage("<red>This can only be ran by players");
                    return false;
                }

                player.openInventory(TagInventories.tagInventory(targetData));
            }
            case "setcurrenttag" -> {
                if(args.length < 3) {
                    sender.sendRichMessage("<red>/chatadmin <player> settag [<addbracket = true>] <tag>\n<gray>(the add bracket option is optional)");
                    return false;
                }

                final String tag = getTagFromArgs(args, 2);
                targetData.setCurrentTag(tag);

                sender.sendRichMessage("<green>Set target's current tag to " + tag);
            }
            case "addtag" -> {
                if(args.length < 3) {
                    sender.sendRichMessage("<red>/chatadmin <player> addtag [<addbracket = true>] <tag>\n<gray>(the add bracket option is optional)");
                    return false;
                }

                final String tag = getTagFromArgs(args, 2);
                targetData.addTag(tag);

                sender.sendRichMessage("<green>Added " + tag + " <green>to target's tags");
            }
            case "removetag" -> {
                if(!(sender instanceof Player player)){
                    sender.sendRichMessage("<red>This can only be ran by players");
                    return false;
                }
                isRemovingTag.put(player, targetData);
                player.openInventory(TagInventories.tagInventory(targetData));
            }

            case "setchatcolor" -> {
                if(args.length < 3) {
                    sender.sendRichMessage("<red>/chatadmin <player> chatcolor <color>");
                    return false;
                }

                targetData.setChatColor(args[2]);
                sender.sendRichMessage("<green>Set target's chat color to " + targetData.getChatColor() + " THIS");
            }
            case "setnamecolor" -> {
                targetData.setNameColor(args[2]);
                sender.sendRichMessage("<green>Set target's name color to " + targetData.getNameColor() + " THIS");
            }
            case "displayname" -> {
                targetData.setDisplayName(String.join(" ", Arrays.copyOfRange(args,2, args.length)));
                sender.sendRichMessage("<green>Set target's display name to " + targetData.getDisplayName());
            }

        }

        return false;
    }

    @SuppressWarnings("SameParameterValue")
    private static String getTagFromArgs(String[] args, int addBracketIndex){
        boolean addBracket = true;
        int startOfTag = addBracketIndex;
        if(List.of("true", "false").contains(args[addBracketIndex].toLowerCase())) {
            addBracket = Boolean.parseBoolean(args[addBracketIndex]);
            startOfTag = addBracketIndex +1;
        }

        String tag = String.join(" ", Arrays.copyOfRange(args, startOfTag, args.length));

        if(addBracket)
            tag = "<dark_gray>[" + tag + "<dark_gray>]";

        return Messages.essentialsToMinimessage(tag);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length){
            case 1 -> EasyTabComplete.correctTabComplete(args[0], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            case 2 -> EasyTabComplete.correctTabComplete(args[1], "newtagitem", "setcurrenttag", "addtag", "removetag", "setchatcolor", "setnamecolor", "displayname");
            default -> List.of();
        };
    }
}
