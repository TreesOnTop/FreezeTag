package com.github.zac694.freezetag.commands;

import com.github.zac694.freezetag.ConfigHandler;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveFreezer {
    public static void register() {
        new CommandAPICommand("removefreezer")
                .withArguments(new PlayerArgument("player"))
                .withPermission("freezetag.removefreezer")
                .withAliases("rf")
                .executes((sender, args) -> {
                    ConfigHandler.getData().set("freezer." + ((Player)args[0]).getUniqueId(), null);
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§b§l[FreezeTag]§r " + ((Player)args[0]).getName() + " has been removed from the freezer team"));
                }).register();
    }
}
