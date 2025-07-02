package poa.poachatv3.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import poa.poachatv3.util.ChatColors;
import poa.poachatv3.util.PlayerData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Chat implements Listener {


    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if (e.isCancelled())
            return;

        final Component componentMessage = e.message();
        final String rawMessage = PlainTextComponentSerializer.plainText().serialize(componentMessage);
        final Player player = e.getPlayer();
        final PlayerData playerData = PlayerData.getPlayerData(player);
        final String chatColor = playerData.getChatColor();
        final String nameColor = playerData.getNameColor();

        final String stringDisplayName = playerData.getDisplayName();

        String tag = playerData.getCurrentTag();

        if (tag.equalsIgnoreCase("££RANDOM££")) {
            final List<String> tags = playerData.getTags();
            if (tags.isEmpty()) {
                tag = "";
                playerData.setCurrentTag("");
            } else
                tag = tags.get(ThreadLocalRandom.current().nextInt(0, tags.size()));
        }

        if (!tag.isEmpty()) {
            tag = " " + tag;
        }


        final Component displayname = MiniMessage.miniMessage().deserialize(nameColor + stringDisplayName + tag);

        e.message(getMessage(rawMessage, chatColor));

        e.renderer((sender, displayName, inputMessage, viewingPlayer) ->
                Component.join(JoinConfiguration.separator(Component.text(" ")), displayname, e.message()));

    }

    private static Component getMessage(String rawMessage, String chatColor) {
        return MiniMessage.miniMessage().deserialize(chatColor + "<string>", Placeholder.unparsed("string", rawMessage));
    }


    @EventHandler(priority = EventPriority.LOW)
    public void customChatColor(AsyncChatEvent e) {
        final Player player = e.getPlayer();
        if (e.isCancelled() || !InvClick.isChoosingCustomColor.contains(player))
            return;

        e.setCancelled(true);

        final Component componentMessage = e.message();
        String rawMessage = PlainTextComponentSerializer.plainText().serialize(componentMessage);
        final PlayerData playerData = PlayerData.getPlayerData(player);

        if (!rawMessage.contains("#"))
            rawMessage = "#" + rawMessage;

        if (!isValidHexColor(rawMessage)) {
            player.sendRichMessage("<red>You must input a hex color for custom chat color. Exiting...");
            InvClick.isChoosingCustomColor.remove(player);
            return;
        }

        playerData.setChatColor("<" + rawMessage + ">");
        player.sendRichMessage(playerData.getChatColor() + "Custom chat color set");
        InvClick.isChoosingCustomColor.remove(player);
    }

    private static boolean isValidHexColor(String input) {
        // Check length and single '#' at start
        if (input == null || input.length() != 7 || input.charAt(0) != '#') {
            return false;
        }

        // Extract the color part after '#'
        String hexPart = input.substring(1);

        // Match only 0-9, a-f, A-F
        return hexPart.matches("[0-9a-fA-F]{6}");
    }


}
