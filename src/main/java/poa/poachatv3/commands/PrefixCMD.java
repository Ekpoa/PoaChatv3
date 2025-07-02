package poa.poachatv3.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poachatv3.PoaChatv3;
import poa.poachatv3.util.PlayerData;
import poa.poalib.luckperms.LuckPerm;
import poa.poalib.messages.Messages;

import java.util.Arrays;
import java.util.UUID;

public class PrefixCMD implements CommandExecutor {

    public static final boolean isBoldPrefix = PoaChatv3.INSTANCE.getConfig().getBoolean("Prefix.bold");
    public static final int maxLength = PoaChatv3.INSTANCE.getConfig().getInt("Prefix.maxlength");
    public static final boolean nameBold = PoaChatv3.INSTANCE.getConfig().getBoolean("Prefix.namebold");




    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        if(args.length == 0){
            player.sendRichMessage("<red>/prefix <prefix>");
            return false;
        }

        final String joined = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

        if(joined.length() > maxLength){
            player.sendRichMessage("<red>You cannot have a prefix longer than " + maxLength);
            return false;
        }

        final String prefix = setPrefix(player.getUniqueId(), joined);
        player.sendRichMessage("<green>Prefix set to " + prefix + "\n use <i><click:run_command:'/prefixcolor'>/prefixcolor <i:false>to set custom color or CLICK HERE");

        return false;
    }

    public static String setPrefix(UUID uuid, String prefix){
        prefix = PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage().deserialize(Messages.essentialsToMinimessage(prefix)));

        if(isBoldPrefix)
            prefix = "<bold>" + prefix;

        if(nameBold)
            prefix = prefix + "<bold>";
        else
            prefix = prefix + "<bold:false>";

        final PlayerData playerData = PlayerData.getPlayerData(uuid);
        playerData.setHasCustomPrefix(true);

        LuckPerm.setPrefix(uuid, playerData.getPrefixColor() + prefix + " ");
        return prefix;
    }

    public static void setPrefixColor(UUID uuid, String color){
        color = Messages.essentialsToMinimessage(color);

        String finalColor = color;
        LuckPerm.getPrefix(uuid).thenAccept(prefix -> {

            String uncolored = PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage().deserialize(Messages.essentialsToMinimessage(prefix)));

            final String colored = finalColor + uncolored;

            LuckPerm.setPrefix(uuid, colored);
        });

        return;
    }

}
