package poa.poachatv3.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import poa.poachatv3.PoaChatv3;
import poa.poachatv3.util.PlayerData;
import poa.poachatv3.util.inventories.holders.ChatColorHolder;

public class InvClose implements Listener {

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if(!(e.getInventory().getHolder(false) instanceof ChatColorHolder holder))
            return;

        Player player = (Player) e.getPlayer();

        Bukkit.getScheduler().runTaskLater(PoaChatv3.INSTANCE, () -> {
            if(player.getOpenInventory().getTopInventory().getHolder(false) instanceof ChatColorHolder)
                return;

            InvClick.firstColorMap.remove(player);
            InvClick.isChoosingGradient.remove(player);
            InvClick.isChoosingNameColor.remove(player);
        }, 2L); // used to ensure they didn't choose a new page


    }

}
