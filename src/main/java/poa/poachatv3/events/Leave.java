package poa.poachatv3.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import poa.poachatv3.util.PlayerData;

public class Leave implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerData.getPlayerData(e.getPlayer()).save(false);
    }
}
