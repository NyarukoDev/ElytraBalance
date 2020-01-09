# ElytraBalance
With the addition of rocket boosted elytra flight, the elytra may be seen as too powerful, especially if other installed plugins make easy the crafting of the item. This plugin seeks to allow server operators to tone down (nerf) the abilities of the elytra through configurable changes to its behavior when gliding and boosting.

Each of the changes (with the current exception of firework star rocket damage) has a matching override permission to allow certain players to bypass these tweaks.

## Controls:
* Damage the elytra when a rocket is used to boost
* Damage to the player when they use rockets that do not have firework stars
* Configure how much damage players take when using rockets with and without firework stars
* Decide if players should be able to use consumable items when gliding (food, potions etc.)
* Decide if the elytra should disappear when it breaks like normal tools
* Decide if the elytra should be repairable in an anvil or by the mending enchantment

## Permissions:
* `elytrabalance.overrides.itemdamage`
* `elytrabalance.overrides.boostplayerdamage`
* `elytrabalance.overrides.breakremoval`
* `elytrabalance.overrides.eat`
* `elytrabalance.overrides.fix`
* `elytrabalance.overrides.mend`

## Configuration:
```
{
  "removeElytraOnBreak": false,
  "itemDamageOnRocketUse": 57,
  "playerDamageOnNoStarRocketUse": true,
  "NoStar": "The damage dealt to a player when they boost with a rocket that is not equipped with a firework star, assuming damage for this is enabled in this plugin.",
  "damagePerNoStarRocketUse": 7.0,
  "Star": "Additional damage dealt to a player when they boost with a rocket that is equipped with a firework star on top of the default 2 and a half hearts.",
  "additionalDamagePerStarRocketUse": 0.0,
  "canConsumeFoodInFlight": true,
  "canRepairElytra": true
  "canMendElytra": true
}
```
