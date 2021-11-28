package it.ivirus.handcuff.commands;

import it.ivirus.handcuff.MainHandcuff;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommandTabCompleter implements TabCompleter {
    private final MainHandcuff plugin;

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        CommandExecutor commandExecutor = plugin.getCommand(command.getName()).getExecutor();
        HandcuffCommandHandler commandHandler = (HandcuffCommandHandler) commandExecutor;
        if (args.length == 1) {
            return new ArrayList<>(commandHandler.getCommands().keySet());
        }

        return null;
    }
}
