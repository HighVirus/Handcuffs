package it.ivirus.handcuff.commands;

import it.ivirus.handcuff.commands.subcommands.GetSubcmd;
import it.ivirus.handcuff.commands.subcommands.GiveSubcmd;
import it.ivirus.handcuff.commands.subcommands.HelpSubcmd;
import it.ivirus.handcuff.commands.subcommands.ReloadSubcmd;
import it.ivirus.handcuff.utils.Strings;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

@Getter
public class HandcuffCommandHandler implements CommandExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    public HandcuffCommandHandler() {
        registerCommand("reload", new ReloadSubcmd());
        registerCommand("help", new HelpSubcmd());
        registerCommand("get", new GetSubcmd());
        registerCommand("give", new GiveSubcmd());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (args.length == 0 || !commands.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(Strings.getFormattedString("&3-------------------------------"));
            sender.sendMessage(Strings.getFormattedString("&3Plugin sviluppato da: &fiVirus_"));
            sender.sendMessage(Strings.getFormattedString("&3Telegram: &fhttps://t.me/HoxijaChannel"));
            sender.sendMessage(Strings.getFormattedString("&3Discord: &fhttps://discord.io/hoxija"));
            sender.sendMessage(Strings.getFormattedString("&3-------------------------------"));
            return true;
        }

        commands.get(args[0].toLowerCase()).onCommand(sender, command, args);
        return true;
    }

    private void registerCommand(String cmd, SubCommand subCommand) {
        commands.put(cmd, subCommand);
    }
}
