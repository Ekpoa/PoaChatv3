package poa.poachatv3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.inventories.TagInventories;

public class TagsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        player.openInventory(TagInventories.tagInventory(PlayerData.getPlayerData(player)));

        return false;
    }
}
