package it.ivirus.handcuff.commands;

import it.ivirus.handcuff.MainHandcuff;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class SubCommand {
    protected final MainHandcuff plugin = MainHandcuff.getInstance();
    protected final FileConfiguration config = plugin.getConfig();
    protected final FileConfiguration langConfig = plugin.getLangConfig();
    protected final MainHandcuff mainHandcuff = MainHandcuff.getInstance();
    protected final BukkitAudiences adventure = plugin.getAdventure();

    public abstract void onCommand(CommandSender sender, Command command, String[] args);
}
