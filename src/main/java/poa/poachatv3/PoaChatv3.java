package poa.poachatv3;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import poa.poachatv3.commands.*;
import poa.poachatv3.events.*;
import poa.poachatv3.util.PlayerData;

public final class PoaChatv3 extends JavaPlugin {

    public static PoaChatv3 INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();


        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Chat(), this);
        pm.registerEvents(new InvClick(), this);
        pm.registerEvents(new InvClose(), this);
        pm.registerEvents(new JoinLeave(), this);
        pm.registerEvents(new Click(), this);

        getCommand("chatcolor").setExecutor(new ChatColorCMD());
        getCommand("chatadmin").setExecutor(new ChatAdmin());
        getCommand("titles").setExecutor(new TagsCMD());
        getCommand("namecolor").setExecutor(new NameColor());
        getCommand("prefix").setExecutor(new PrefixCMD());
        getCommand("prefixcolor").setExecutor(new PrefixColor());

        for (Player p : Bukkit.getOnlinePlayers()) {
            JoinLeave.joinSetup(p);
        }

    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            final PlayerData playerData = PlayerData.getPlayerData(p);
            if(playerData.isSaving())
                continue;

            playerData.save(false);
        }
    }
}
