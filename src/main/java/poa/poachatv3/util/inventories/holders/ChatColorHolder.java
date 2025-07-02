package poa.poachatv3.util.inventories.holders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
public class ChatColorHolder implements InventoryHolder {

    int page;
    boolean choosingGradient;
    boolean choosingNameColor;

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
