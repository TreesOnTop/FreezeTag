package com.github.zac694.freezetag;

import com.github.zac694.freezetag.commands.AddFreezer;
import com.github.zac694.freezetag.commands.FreezeTagCommand;
import com.github.zac694.freezetag.commands.RemoveFreezer;
import com.github.zac694.freezetag.events.EntityDamage;
import com.github.zac694.freezetag.events.EntityDamageByEntity;
import com.github.zac694.freezetag.events.PlayerMove;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("ConstantConditions")
public final class FreezeTag extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigHandler.setup();
        mainClass = this;
        CommandAPI.onEnable(this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        AddFreezer.register();
        FreezeTagCommand.register();
        RemoveFreezer.register();
        ConfigHandler.getData().set("time", ConfigHandler.getConfig().get("matchtime"));
        ConfigHandler.save();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(mainClass, () -> {
            if(ConfigHandler.getData().get("time") == null){
                ConfigHandler.getData().set("time", 0);
            }
            if(((int)ConfigHandler.getData().get("time"))>((int)ConfigHandler.getConfig().get("matchtime"))){
                Bukkit.getOnlinePlayers().forEach(player -> ConfigHandler.getData().set("frozen." + player.getUniqueId(), null));
                Bukkit.getOnlinePlayers().forEach(player -> player.removePotionEffect(PotionEffectType.GLOWING));
                return;
            }
            String s = String.valueOf(((int)ConfigHandler.getConfig().get("matchtime")) - ((int)ConfigHandler.getData().get("time")));
            Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(s)));
            ConfigHandler.getData().set("time", ((int)ConfigHandler.getData().get("time"))+1);
            ConfigHandler.save();
        }, 0, 20);
    }
    public void onLoad(){
        CommandAPI.onLoad(new CommandAPIConfig());
    }
    @Override
    public void onDisable() {

    }
    private static FreezeTag mainClass;
    public static FreezeTag getMainClass() { return mainClass; }
}
