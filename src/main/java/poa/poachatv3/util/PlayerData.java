package poa.poachatv3.util;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import poa.poachatv3.PoaChatv3;
import poa.poalib.messages.Messages;
import poa.poalib.yml.PoaYaml;

import java.io.File;
import java.util.*;

@Getter
public class PlayerData {

    public static final Map<UUID, PlayerData> dataMap = new HashMap<>();
    private static final File folder = new File(PoaChatv3.INSTANCE.getDataFolder(), "playerdata");



    static {
        folder.mkdirs();

        Bukkit.getScheduler().runTaskTimerAsynchronously(PoaChatv3.INSTANCE, () -> {
            if(dataMap.isEmpty())
                return;

            for (PlayerData value : dataMap.values()) {
                if(value.isSaving())
                    continue;

                value.setSaving(true);
                value.save(true);
                value.setSaving(false);
            }
        }, 600L, 6000L);

    }

    public static PlayerData getPlayerData(UUID uuid){
        if(dataMap.containsKey(uuid))
            return dataMap.get(uuid);
        return new PlayerData(uuid);
    }

    public static PlayerData getPlayerData(Player player){
        return getPlayerData(player.getUniqueId());
    }


    UUID uuid;
    String chatColor = Messages.essentialsToMinimessage(Objects.requireNonNull(PoaChatv3.INSTANCE.getConfig().getString("DefaultChatColor")));
    String nameColor = Messages.essentialsToMinimessage(Objects.requireNonNull(PoaChatv3.INSTANCE.getConfig().getString("DefaultNameColor")));
    String displayName;

    String currentTag = "";
    List<String> tags = new ArrayList<>();

    File file;
    PoaYaml yml;

    @Setter
    boolean isSaving = false;

    private PlayerData(UUID uuid){
        this.uuid = uuid;

        file = new File(folder, uuid + ".yml");
        yml = PoaYaml.loadFromFile(file, true);

        if(yml.isSet("ChatColor"))
            this.chatColor = yml.getString("ChatColor");

        if(yml.isSet("NameColor"))
            this.nameColor = yml.getString("NameColor");

        if(yml.isSet("DisplayName"))
            this.displayName = yml.getString("DisplayName");
        else
            this.displayName = Bukkit.getOfflinePlayer(uuid).getName();

        if(yml.isSet("Tags"))
            this.tags = yml.getStringList("Tags");

        if(yml.isSet("CurrentTag"))
            this.currentTag = yml.getString("CurrentTag");

        dataMap.put(uuid, this);
    }

    public void setChatColor(String chatColor) {
        this.chatColor = Messages.essentialsToMinimessage(chatColor);
    }

    public void setNameColor(String nameColor) {
        this.nameColor = Messages.essentialsToMinimessage(nameColor);
    }

    public void setDisplayName(String displayName) {
        this.displayName = PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage().deserialize(Messages.essentialsToMinimessage(displayName)));
        final OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
        if(target.isOnline())
            Objects.requireNonNull(target.getPlayer()).displayName(MiniMessage.miniMessage().deserialize(displayName));
    }

    public void addTag(String tag){
        tags.add(Messages.essentialsToMinimessage(tag));
    }

    public void removeTag(String tag){
        tags.remove(Messages.essentialsToMinimessage(tag));
    }

    public void setCurrentTag(String currentTag) {
        this.currentTag = Messages.essentialsToMinimessage(currentTag);
    }

    @SneakyThrows
    public void save(boolean async){
        yml.set("ChatColor", chatColor);
        yml.set("NameColor", nameColor);
        yml.set("DisplayName", displayName);
        yml.set("Tags", tags);
        yml.set("CurrentTag", currentTag);

        if(async)
            yml.saveAsync(file);
        else {
            yml.save(file);
        }
    }

    public void unload(){
        dataMap.remove(uuid);
    }



}
