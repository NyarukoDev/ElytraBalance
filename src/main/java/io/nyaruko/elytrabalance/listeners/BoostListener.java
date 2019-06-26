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
import org.bukkit.inventory.meta.ItemMeta;

public class BoostListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBoost(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.isGliding()) {
            ItemStack is = event.getItem();
            if ((is != null) && (is.getType() == Material.FIREWORK_ROCKET)) {
                if (((FireworkMeta) is.getItemMeta()).hasEffects()) {
                    p.damage(ElytraBalance.getConfigModel().additionalDamagePerStarRocketUse);
                } else if (ElytraBalance.getConfigModel().playerDamageOnNoStarRocketUse) {
                    p.damage(ElytraBalance.getConfigModel().damagePerNoStarRocketUse);
                }
                if (!p.hasPermission("elytrabalance.overrides.itemdamage")) {
                    ItemStack elytra = p.getInventory().getChestplate();
                    if(elytra != null) {
                        ItemMeta meta = elytra.getItemMeta();
                        if(meta != null) {
                            int durability = ((Damageable) meta).getDamage() + ElytraBalance.getConfigModel().itemDamageOnRocketUse;
                            if (durability > 431) {
                                durability = 431;
                            }
                            ((Damageable) meta).setDamage(durability);
                        }
                    }
                }
            }
        }
    }
}
