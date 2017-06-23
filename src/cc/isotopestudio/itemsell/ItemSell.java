package cc.isotopestudio.itemsell;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemSell extends JavaPlugin {

    private static final String pluginName = "ItemSell";
    static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("ItemSell").append("]").append(ChatColor.RED).toString();

    static ItemSell plugin;

    static PluginFile config;

    static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("����Vault API");
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - �����ر��޷����룬ԭ��Vaultδ��װ",
                    getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);

        this.getCommand("pls").setExecutor(new SellCommand());
        SellConfig.init();

        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "�ɹ�ж��!");
    }

    // Vault API
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
        return (econ != null);
    }
}
