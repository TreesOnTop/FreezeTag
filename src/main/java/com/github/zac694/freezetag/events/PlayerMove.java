package com.github.zac694.freezetag.events;

import com.github.zac694.freezetag.ConfigHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(!event.isCancelled() && ConfigHandler.getData().getBoolean("frozen." + event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }
}
