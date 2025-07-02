package poa.poachatv3.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import poa.poachatv3.util.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class JoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        joinSetup(e.getPlayer());
    }

    public static void joinSetup(Player player){
        List<String> list = new ArrayList<>();

        list.add("[help]");

        if (player.hasPermission("poa.chat.modules.item"))
            list.add("[item]");
        if (player.hasPermission("poa.chat.modules.kills"))
            list.add("[kills]");
        if (player.hasPermission("poa.chat.modules.deaths"))
            list.add("[deaths]");
        if (player.hasPermission("poa.chat.modules.kdr"))
            list.add("[item]");
        if (player.hasPermission("poa.chat.modules.playtime"))
            list.add("[playtime]");
        if (player.hasPermission("poa.chat.modules.inventory"))
            list.add("[inventory]");
        if (player.hasPermission("poa.chat.modules.enderchest"))
            list.add("[enderchest]");
        if (player.hasPermission("poa.chat.modules.timesincedeath"))
            list.add("[time since death]");
        if (player.hasPermission("poa.chat.modules.balance"))
            list.add("[balance]");
        if (player.hasPermission("poa.chat.modules.titles"))
            list.add("[titles]");
        if (player.hasPermission("poa.chat.modules.coords"))
            list.add("[coords]");
        if (player.hasPermission("poa.chat.modules.statistic"))
            list.add("[statistic]");
        if (player.hasPermission("poa.chat.modules.killed"))
            list.add("[killed]");
        if (player.hasPermission("poa.chat.modules.killedby"))
            list.add("[killedby]");
        if (player.hasPermission("poa.chat.modules.placed"))
            list.add("[placed]");
        if (player.hasPermission("poa.chat.modules.mined"))
            list.add("[mined]");
        if (player.hasPermission("poa.chat.modules.pv"))
            list.add("[pv]");
        if (player.hasPermission("poa.chat.modules.level"))
            list.add("[level]");

        player.addCustomChatCompletions(list);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerData.getPlayerData(e.getPlayer()).save(false);
    }
}
