package com.github.zac694.freezetag.commands;

import com.github.zac694.freezetag.ConfigHandler;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddFreezer {
    public static void register() {
        new CommandAPICommand("addfreezer")
                .withArguments(new PlayerArgument("player"))
                .withPermission("freezetag.addfreezer")
                .withAliases("af")
                .executes((sender, args) -> {
                    ConfigHandler.getData().set("freezer." + ((Player)args[0]).getUniqueId(), true);
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§b§l[FreezeTag]§r " + ((Player)args[0]).getName() + " has been added to the freezer team"));
        }).register();
    }
}
