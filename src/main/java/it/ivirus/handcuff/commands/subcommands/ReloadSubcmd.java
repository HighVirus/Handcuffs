package it.ivirus.handcuff.commands.subcommands;

import it.ivirus.handcuff.commands.SubCommand;
import it.ivirus.handcuff.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("handcuffs.admin")) {
            adventure.sender(sender).sendMessage(Strings.ERROR_NOPERMISSION.getFormattedString());
            return;
        }
        plugin.reloadConfig();
        plugin.loadLangConfig();
        adventure.sender(sender).sendMessage(Strings.INFO_RELOAD.getFormattedString());
    }
}
