package it.ivirus.handcuff.commands.subcommands;

import it.ivirus.handcuff.commands.SubCommand;
import it.ivirus.handcuff.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("handcuff.help")) {
            sender.sendMessage(Strings.ERROR_NOPERMISSION.getFormattedString());
            return;
        }
        sender.sendMessage(Strings.INFO_HELP.getFormattedString());
    }
}
