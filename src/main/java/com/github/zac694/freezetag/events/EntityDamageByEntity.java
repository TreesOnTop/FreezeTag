package com.github.zac694.freezetag.events;

import com.github.zac694.freezetag.ConfigHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE;
import static org.bukkit.Sound.BLOCK_LAVA_EXTINGUISH;

public class EntityDamageByEntity implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(((int)ConfigHandler.getConfig().get("peacetime"))<((int)ConfigHandler.getData().get("time")) && ((int)ConfigHandler.getConfig().get("matchtime"))>((int)ConfigHandler.getData().get("time"))) {
            if (event.getEntity() instanceof Player) {
                if (ConfigHandler.getData().getBoolean("freezer." + event.getDamager().getUniqueId())) {
                    ConfigHandler.getData().set("frozen." + event.getEntity().getUniqueId(), true);
                    event.getEntity().getWorld().playSound(event.getEntity().getLocation(), BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1, 1);
                    ((Player)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000000, 1));
                } else if (event.getDamager() instanceof Player && ConfigHandler.getData().getBoolean("frozen." + event.getEntity().getUniqueId())) {
                    ConfigHandler.getData().set("frozen." + event.getEntity().getUniqueId(), null);
                    event.getEntity().getWorld().playSound(event.getEntity().getLocation(), BLOCK_LAVA_EXTINGUISH, 1, 1);
                    ((Player)event.getEntity()).removePotionEffect(PotionEffectType.GLOWING);
                }
                ConfigHandler.save();
            }
        }
    }
}
