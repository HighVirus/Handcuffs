package it.ivirus.handcuff.commands.subcommands;

import it.ivirus.handcuff.commands.SubCommand;
import it.ivirus.handcuff.utils.HandcuffUtil;
import it.ivirus.handcuff.utils.Strings;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("handcuffs.give")) {
            sender.sendMessage(Strings.ERROR_NOPERMISSION.getFormattedString());
            return;
        }
        if (args.length != 2 && args.length != 3) {
            sender.sendMessage(Strings.ERROR_USAGE_GIVE.getFormattedString());
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(Strings.ERROR_TARGET_OFFLINE.getFormattedString());
            return;
        }
        int amount = 1;
        if (args.length == 2) {
            target.getInventory().addItem(HandcuffUtil.getHandcuffItem(amount));
        } else {
            if (!NumberUtils.isDigits(args[2])) {
                sender.sendMessage(Strings.ERROR_INVALID_VALUE.getFormattedString());
                return;
            }
            amount = Integer.parseInt(args[2]);
            if (amount < 1) {
                sender.sendMessage(Strings.ERROR_INVALID_VALUE.getFormattedString());
                return;
            }
            target.getInventory().addItem(HandcuffUtil.getHandcuffItem(amount));
        }
        target.sendMessage(Strings.INFO_HANDCUFFS_GET.getFormattedString());
        sender.sendMessage(Strings.INFO_HANDCUFFS_GIVE.getFormattedString()
                .replaceAll("%amount%", String.valueOf(amount))
                .replaceAll("%target_name%", target.getName()));

    }
}