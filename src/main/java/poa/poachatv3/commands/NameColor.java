package poa.poachatv3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import poa.poachatv3.events.InvClick;
import poa.poachatv3.util.inventories.ColorInventory;
import poa.poachatv3.util.inventories.holders.ChatColorHolder;

import java.util.Objects;

public class NameColor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        InvClick.isChoosingNameColor.add(player);
        final Inventory inventory = ColorInventory.colorInventory(player);
        ((ChatColorHolder) Objects.requireNonNull(inventory.getHolder(false))).setChoosingNameColor(true);
        player.openInventory(inventory);

        return false;
    }
}
