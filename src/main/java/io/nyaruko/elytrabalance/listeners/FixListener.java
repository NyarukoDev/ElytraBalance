package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.ItemStack;

public class FixListener implements Listener {
    /**
     * Listener for anvil repairs (canRepairElytra)
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFix(PrepareAnvilEvent event) {
        Player p = (Player) event.getView().getPlayer();
        ItemStack rightSide = event.getInventory().getContents()[1];//Right hand slot of anvil

        if (event.getResult() != null &&
            event.getResult().getType() == Material.ELYTRA &&
            rightSide != null &&
            rightSide.getType() == Material.PHANTOM_MEMBRANE && //Allows users to still enchant elytras
            !ElytraBalance.getConfigModel().canRepairElytra &&
            !p.hasPermission("elytrabalance.overrides.fix")) {
            event.setResult(null);
            p.updateInventory();
            if(ElytraBalance.getConfigModel().showRepairAttemptBlockedMessage)
                ElytraBalance.sendConfigMessage(p, ElytraBalance.getConfigModel().repairAttemptBlockedMessage);
        }
    }

    /**
     * Listener for mend enchantment repairs (canMendElytra)
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFix(PlayerItemMendEvent event) {
        Player p = event.getPlayer();
        if(event.getItem().getType() == Material.ELYTRA && !ElytraBalance.getConfigModel().canMendElytra && !p.hasPermission("elytrabalance.overrides.mend")){
            event.setRepairAmount(0);
        }
    }
}
