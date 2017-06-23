package cc.isotopestudio.itemsell;
/*
 * Created by Mars Tan on 6/23/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.ChatColor;

abstract class SellConfig {

    static String sellValuePrefix;
    static String sellValueCurrency;

    static String msgItem;
    static String msgSuccess;

    static void init() {
        sellValuePrefix = convert(ItemSell.config.getString("sellValue.prefix"));
        sellValueCurrency = convert(ItemSell.config.getString("sellValue.currency"));
        msgItem = convert(ItemSell.config.getString("msg.itemError"));
        msgSuccess = convert(ItemSell.config.getString("msg.success"));
    }

    private static String convert(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
