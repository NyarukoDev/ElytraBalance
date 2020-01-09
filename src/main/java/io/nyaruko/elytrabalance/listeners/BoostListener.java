package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;

public class BoostListener implements Listener {
    /**
     * Listener for elytra boost events (playerDamageOnNoStarRocketUse)
     * Player damage amounts defined with additionalDamagePerStarRocketUse & damagePerNoStarRocketUse
     * Elytra ItemStack damage defined with itemDamageOnRocketUse
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBoost(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack heldItem = event.getItem();
        if(!p.isGliding() || heldItem == null || heldItem.getType() != Material.FIREWORK_ROCKET) return;

        //Should deal player damage checks
        if(!p.hasPermission("elytrabalance.overrides.boostplayerdamage")) {
            if(((FireworkMeta) heldItem.getItemMeta()).hasEffects()) {
                p.damage(ElytraBalance.getConfigModel().additionalDamagePerStarRocketUse);
            } else if(ElytraBalance.getConfigModel().playerDamageOnNoStarRocketUse) {
                p.damage(ElytraBalance.getConfigModel().damagePerNoStarRocketUse);
            }
        }

        //Should damage elytra checks
        if(!p.hasPermission("elytrabalance.overrides.itemdamage")) {
            ItemStack elytra = p.getInventory().getChestplate();
            if(elytra != null) {
                Damageable m = (Damageable) elytra.getItemMeta();
                if(m != null) {
                    int durability = m.getDamage() + ElytraBalance.getConfigModel().itemDamageOnRocketUse;
                    durability = Math.min(durability, 431);
                    m.setDamage(durability);
                }
            }
        }
    }
}
