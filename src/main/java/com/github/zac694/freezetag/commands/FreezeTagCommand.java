package com.github.zac694.freezetag.commands;

import com.github.zac694.freezetag.ConfigHandler;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FreezeTagCommand {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void register(){
        new CommandAPICommand("freezetag")
                .withArguments(new GreedyStringArgument("string"))
                .withPermission("freezetag.freezetag")
                .withAliases("ft")
                .executesPlayer((sender, args) -> {
                    String string = (String)args[0];
                    switch (string) {
                        case "reload":
                            ConfigHandler.reload();
                            sender.sendMessage("§b§l[FreezeTag]§r Plugin reloaded");
                            break;
                        case "resetconfig":
                            ConfigHandler.getConfigFile().delete();
                            ConfigHandler.setup();
                            sender.sendMessage("§b§l[FreezeTag]§r Config reset");
                            break;
                        case "resetdata":
                            ConfigHandler.getDataFile().delete();
                            ConfigHandler.setup();
                            sender.sendMessage("§b§l[FreezeTag]§r Data reset");
                            break;
                        case "start":
                            int x = (int) (Math.random() * 69420);
                            int z = (int) (Math.random() * 69420);
                            World world = sender.getWorld();
                            WorldBorder worldBorder = world.getWorldBorder();
                            Bukkit.getOnlinePlayers().forEach(player -> ConfigHandler.getData().set("frozen." + player.getUniqueId(), null));
                            Bukkit.getOnlinePlayers().forEach(player -> player.removePotionEffect(PotionEffectType.GLOWING));
                            Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().clear());
                            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(new Location(world, x, 128, z, 0, 0)));
                            List<String> items = new ArrayList<>(ConfigHandler.getConfig().getStringList("FreezerItems"));
                            for(Player p : Bukkit.getOnlinePlayers()){
                                if(ConfigHandler.getData().getBoolean("freezer." + p.getUniqueId())) {
                                    for(String item : items){
                                        p.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(item)), 1));
                                    }
                                }
                            }
                            worldBorder.setCenter(x, z);
                            worldBorder.setSize(50);
                            sender.sendMessage("§b§l[FreezeTag]§r Game started");
                            ConfigHandler.getData().set("time", 0);
                            ConfigHandler.save();
                            break;
                        default:
                            sender.sendMessage("§6--- freezetag help ---");
                            sender.sendMessage("/freezetag - Show this");
                            sender.sendMessage("/freezetag reload - reloads config");
                            sender.sendMessage("/freezetag resetconfig - deletes config file and creates a new one");
                            sender.sendMessage("/freezetag resetdata - deletes data file and creates a new one");
                            sender.sendMessage("/freezetag start - starts the game");
                            sender.sendMessage("/addfreezer - adds a player to the freezer team");
                            sender.sendMessage("/removefreezer - removes a player from the freezer team");
                            sender.sendMessage("");
                            break;
                    }
                }).register();
        new CommandAPICommand("freezetag")
                .withPermission(CommandPermission.fromString("freezetag.freezetag"))
                .withAliases("ft")
                .executesPlayer((sender, args) -> {
                    sender.sendMessage("§6--- freezetag help ---");
                    sender.sendMessage("/freezetag - Show this");
                    sender.sendMessage("/freezetag reload - reloads config");
                    sender.sendMessage("/freezetag resetconfig - deletes config file and creates a new one");
                    sender.sendMessage("/freezetag resetdata - deletes data file and creates a new one");
                    sender.sendMessage("/freezetag start - starts the game");
                    sender.sendMessage("/addfreezer - adds a player to the freezer team");
                    sender.sendMessage("/removefreezer - removes a player from the freezer team");
                    sender.sendMessage("");
                }).register();
    }
}
