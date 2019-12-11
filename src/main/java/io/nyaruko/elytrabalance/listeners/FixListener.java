package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerItemMendEvent;

public class FixListener implements Listener {

    //Repair (Anvil)
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFix(PrepareAnvilEvent event) {
        Player p = (Player) event.getView().getPlayer();
        if ((event.getResult().getType() == Material.ELYTRA) && (!ElytraBalance.getConfigModel().canRepairElytra) && (!p.hasPermission("elytrabalance.overrides.fix"))) {
            event.setResult(null);
            p.updateInventory();
        }
    }

    //Mend (Enchantment)
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFix(PlayerItemMendEvent event) {
        Player p = event.getPlayer();
        if((event.getItem().getType() == Material.ELYTRA) && (!ElytraBalance.getConfigModel().canMendElytra) && (!p.hasPermission("elytrabalance.overrides.mend"))){
            event.setRepairAmount(0);
        }
    }
}
