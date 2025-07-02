package poa.poachatv3.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import poa.poachatv3.util.PlayerData;
import poa.poalib.shaded.NBT;

public class Click implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.useItemInHand() == Event.Result.DENY)
            return;

        final Player player = e.getPlayer();
        final ItemStack item = player.getInventory().getItemInMainHand();

        if(e.getAction().isLeftClick())
            return;

        if(item.isEmpty())
            return;

        String tag = NBT.get(item, nbt -> {
            if(!nbt.hasTag("PoaChatTag"))
                return null;

            return nbt.getString("PoaChatTag");
        });

        if(tag == null)
            return;

        final PlayerData playerData = PlayerData.getPlayerData(player);

        if(playerData.getTags().contains(tag)){
            player.sendRichMessage("<red>You already own this tag");
            return;
        }

        playerData.addTag(tag);
        playerData.setCurrentTag(tag);

        player.sendRichMessage("<green>Claimed tag " + tag);

        item.setAmount(item.getAmount() -1);
    }

}
