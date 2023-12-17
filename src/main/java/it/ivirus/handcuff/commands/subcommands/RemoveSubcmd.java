package it.ivirus.handcuff.commands.subcommands;

import it.ivirus.handcuff.commands.SubCommand;
import it.ivirus.handcuff.data.HandcuffData;
import it.ivirus.handcuff.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSubcmd extends SubCommand {
    private final HandcuffData handcuffData = HandcuffData.getInstance();
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("handcuffs.remove")) {
            adventure.sender(sender).sendMessage(Strings.ERROR_NOPERMISSION.getFormattedString());
            return;
        }
        if (args.length != 2) {
            adventure.sender(sender).sendMessage(Strings.ERROR_USAGE_REMOVE.getFormattedString());
            return;
        }
        String playerName = args[1];
        Player target = Bukkit.getPlayer(playerName);
        if (target == null) {
            adventure.sender(sender).sendMessage(Strings.ERROR_TARGET_OFFLINE.getFormattedString());
            return;
        }
        if (!handcuffData.isHandCuffed(target.getUniqueId())) {
            adventure.sender(sender).sendMessage(Strings.ERROR_TARGET_NOT_HANDCUFFED.getFormattedString());
            return;
        }

        if (handcuffData.isHandCuffed(target.getUniqueId())) {
            handcuffData.getPlayerCuff().remove(target.getUniqueId());
            handcuffData.getTargetIsDragged().remove(target);
            plugin.getAdventure().player(target).sendMessage(Strings.INFO_HANDCUFF_REMOVED.getFormattedString());
            plugin.getAdventure().sender(sender).sendMessage(Strings.getFormattedString(Strings.INFO_SENDER_HANDCUFF_REMOVED.getString()
                    .replaceAll("%target_name%", target.getName())));
        }
    }
}
