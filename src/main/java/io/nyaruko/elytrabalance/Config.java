package io.nyaruko.elytrabalance;

//ConfigVersion 2

public class Config {
    //Default to '1' in case of pre-versioning config
    public int configVersion = 1;
    public boolean removeElytraOnBreak = false;
    public int itemDamageOnRocketUse = 57;
    public boolean playerDamageOnNoStarRocketUse = true;
    String NoStar = "The damage dealt to a player when they boost with a rocket that is not equipped with a firework star, assuming damage for this is enabled in this plugin.";
    public double damagePerNoStarRocketUse = 7.0D;
    String Star = "Additional damage dealt to a player when they boost with a rocket that is equipped with a firework star on top of the default 2 and a half hearts.";
    public double additionalDamagePerStarRocketUse = 0.0D;
    public boolean canConsumeFoodInFlight = true;
    public boolean canRepairElytra = true;
    public boolean canMendElytra = true;

    public Config(int configVersion) {
        this.configVersion = configVersion;
    }
}
