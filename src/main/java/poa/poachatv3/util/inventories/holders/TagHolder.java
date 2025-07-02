package poa.poachatv3.util.inventories.holders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import poa.poachatv3.util.PlayerData;

@Getter
@Setter
public class TagHolder implements InventoryHolder {

    int page;

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
