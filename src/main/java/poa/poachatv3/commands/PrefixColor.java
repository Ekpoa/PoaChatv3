package poa.poachatv3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poachatv3.events.InvClick;
import poa.poachatv3.util.inventories.ColorInventory;

public class PrefixColor implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        InvClick.isChoosingPrefixColor.add(player);
        player.openInventory(ColorInventory.colorInventory(player, 0));


        return false;
    }
}
