package it.ivirus.handcuff.commands;

import it.ivirus.handcuff.MainHandcuff;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommandTabCompleter implements TabCompleter {
    private final MainHandcuff plugin;

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> subcommands = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], ((HandcuffCommandHandler) plugin.getCommand(command.getName()).getExecutor()).getCommands().keySet(), subcommands);
            return subcommands;
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("remove"))) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(playerName -> playerName.toLowerCase().startsWith(args[1].toLowerCase())).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}