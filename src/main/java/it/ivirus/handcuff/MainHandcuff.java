package it.ivirus.handcuff;

import it.ivirus.handcuff.commands.CommandTabCompleter;
import it.ivirus.handcuff.commands.HandcuffCommandHandler;
import it.ivirus.handcuff.listener.PlayerListener;
import it.ivirus.handcuff.utils.HandcuffUtil;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class MainHandcuff extends JavaPlugin {

    @Getter
    private static MainHandcuff instance;
    private File langFile;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.createLangFile("en_US", "it_IT");
        this.loadLangConfig();
        getCommand("handcuff").setExecutor(new HandcuffCommandHandler());
        getCommand("handcuff").setTabCompleter(new CommandTabCompleter(this));
        HandcuffUtil.loadListeners(this, new PlayerListener(this));

        int pluginId = 13431;
        new Metrics(this, pluginId);

        getLogger().info("---------------------------------------");
        getLogger().info("Handcuff by iVirus_");
        getLogger().info("Version: " + this.getDescription().getVersion());
        getLogger().info("Discord support: https://discord.io/hoxija");
        getLogger().info("Telegram channel: https://t.me/HoxijaChannel");
        getLogger().info("Plugin is ready!");
        getLogger().info("---------------------------------------");
    }

    private void createLangFile(String... names) {
        for (String name : names) {
            if (!new File(getDataFolder(), "lang" + File.separator + name + ".yml").exists()) {
                saveResource("lang" + File.separator + name + ".yml", false);
            }
        }
    }

    public void loadLangConfig() {
        langFile = new File(getDataFolder(), "lang" + File.separator + getConfig().getString("lang") + ".yml");
        if (!langFile.exists()) {
            langFile = new File(getDataFolder(), "lang" + File.separator + "en_US.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
