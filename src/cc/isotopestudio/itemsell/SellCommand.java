package cc.isotopestudio.itemsell;
/*
 * Created by Mars Tan on 6/23/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cc.isotopestudio.itemsell.ItemSell.config;
import static cc.isotopestudio.itemsell.SellConfig.msgItem;
import static cc.isotopestudio.itemsell.SellConfig.msgSuccess;

class SellCommand implements CommandExecutor {

    private static final String PATTERN = "\\d+";
    private static final Pattern r = Pattern.compile(PATTERN);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pls")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Íæ¼ÒÖ´ÐÐµÄÃüÁî");
                return true;
            }
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || item.getType() == Material.AIR ||
                    !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                player.sendMessage(msgItem);
                return true;
            }
            double value;
            Optional<String> first = item.getItemMeta().getLore().stream()
                    .filter(s -> s.contains(SellConfig.sellValuePrefix) &&
                            s.contains(SellConfig.sellValueCurrency))
                    .findFirst();
            if (!first.isPresent()) {
                player.sendMessage(msgItem);
                return true;
            }
            Optional<Double> doubleResult = first.map(s -> {
                        s = ChatColor.stripColor(s);
                        Matcher m = r.matcher(s);
                        if (m.find()) {
                            try {
                                return Double.parseDouble(m.group(0));
                            } catch (NumberFormatException e) {
                                return -1D;
                            }
                        } else {
                            player.sendMessage(msgItem);
                            return -1D;
                        }
                    });
            if (doubleResult.isPresent()) {
                value = doubleResult.get();
                if (value < 0) {
                    player.sendMessage(msgItem);
                    return true;
                }
            } else {
                player.sendMessage(msgItem);
                return true;
            }
            ItemSell.econ.depositPlayer(player, value);
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(msgSuccess);
            return true;
        }
        return false;
    }
}