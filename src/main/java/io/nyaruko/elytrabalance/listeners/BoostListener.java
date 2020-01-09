package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

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
        if(!p.isGliding() || heldItem == null || event.getAction() != Action.RIGHT_CLICK_AIR || heldItem.getType() != Material.FIREWORK_ROCKET) return;

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
                    durability = Math.min(durability, 432);
                    m.setDamage(durability);
                    elytra.setItemMeta((ItemMeta) m);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerItemDamageEvent(p, elytra, ElytraBalance.getConfigModel().itemDamageOnRocketUse));
                }
            }
        }
    }

    @EventHandler
    public void onRipTide(PlayerRiptideEvent event) {
        final Player player = event.getPlayer();
        ItemStack elytra = player.getInventory().getChestplate();
        if(elytra != null && elytra.getType() == Material.ELYTRA && player.isGliding() && ElytraBalance.getConfigModel().riptideInterruptsGliding && !player.hasPermission("elytrabalance.overrides.riptide")) {
            player.setGliding(false);
            if(ElytraBalance.getConfigModel().showRiptideInterruptMessage)
                ElytraBalance.sendConfigMessage(player, ElytraBalance.getConfigModel().riptideInterruptMessage);
        }
    }
}
