package it.ivirus.handcuff.commands.subcommands;

import it.ivirus.handcuff.commands.SubCommand;
import it.ivirus.handcuff.utils.HandcuffUtil;
import it.ivirus.handcuff.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetSubcmd extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("handcuff.get")) {
            sender.sendMessage(Strings.ERROR_NOPERMISSION.getFormattedString());
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.ERROR_ONLY_PLAYER.getFormattedString());
            return;
        }
        Player player = (Player) sender;
        player.getInventory().addItem(HandcuffUtil.getHandcuffItem(1));
        sender.sendMessage(Strings.INFO_HANDCUFFS_GET.getFormattedString());

    }
}
