package poa.poachatv3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.inventories.ColorInventory;
import poa.poachatv3.util.inventories.TagInventories;
import poa.poachatv3.util.inventories.holders.ChatColorHolder;
import poa.poalib.tabcomplete.EasyTabComplete;

import java.util.List;

public class ChatColorCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        if(args.length == 0){
            player.openInventory(ColorInventory.chatColorMainInventory(player));
            return false;
        }

        switch (args[0].toLowerCase()){
            case "color" -> player.openInventory(ColorInventory.colorInventory(player));
            case "morecolors" -> player.openInventory(ColorInventory.colorInventory(player, 1));
            case "tags","titles" -> player.openInventory(TagInventories.tagInventory(PlayerData.getPlayerData(player)));
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1)
            return EasyTabComplete.correctTabComplete(args[0], "color", "morecolors", "tags", "titles");
        return List.of();
    }
}
