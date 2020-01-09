package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class EatListener implements Listener {
    /**
     * Listener for limiting use of consumables in flight (canConsumeFoodInFlight)
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEat(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        if(p.isGliding() && !p.isSwimming() && !p.hasPermission("elytrabalance.overrides.eat")) {
            event.setCancelled(true);

            if(ElytraBalance.getConfigModel().showConsumableBlockedMessage)
                ElytraBalance.sendConfigMessage(event.getPlayer(), ElytraBalance.getConfigModel().consumableBlockedMessage);
        }
    }
}
