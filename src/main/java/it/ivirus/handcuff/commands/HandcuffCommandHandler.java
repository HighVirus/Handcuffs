package it.ivirus.handcuff.commands;

import it.ivirus.handcuff.MainHandcuff;
import it.ivirus.handcuff.commands.subcommands.*;
import it.ivirus.handcuff.utils.Strings;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

@Getter
public class HandcuffCommandHandler implements CommandExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();
    private final MainHandcuff plugin;
    private final BukkitAudiences adventure;

    public HandcuffCommandHandler(MainHandcuff plugin) {
        this.plugin = plugin;
        this.adventure = plugin.getAdventure();
        registerCommand("reload", new ReloadSubcmd());
        registerCommand("help", new HelpSubcmd());
        registerCommand("get", new GetSubcmd());
        registerCommand("give", new GiveSubcmd());
        registerCommand("remove", new RemoveSubcmd());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (args.length == 0 || !commands.containsKey(args[0].toLowerCase())) {
            adventure.sender(sender).sendMessage(Strings.getFormattedString("&3-------------------------------"));
            adventure.sender(sender).sendMessage(Strings.getFormattedString("&3Plugin developer: &fiVirus_"));
            adventure.sender(sender).sendMessage(Strings.getFormattedString("&3Telegram: &fhttps://t.me/HoxijaChannel"));
            adventure.sender(sender).sendMessage(Strings.getFormattedString("&3Discord: &fhttps://discord.io/hoxija"));
            adventure.sender(sender).sendMessage(Strings.getFormattedString("&3-------------------------------"));
            return true;
        }

        commands.get(args[0].toLowerCase()).onCommand(sender, command, args);
        return true;
    }

    private void registerCommand(String cmd, SubCommand subCommand) {
        commands.put(cmd, subCommand);
    }
}