package kz.hxncus.mc.advancedapi.bukkit.command.info;

import kz.hxncus.mc.advancedapi.api.bukkit.command.info.ExecutionInfo;
import kz.hxncus.mc.advancedapi.bukkit.command.CommandArguments;
import lombok.Data;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Data
public class SenderExecutionInfo implements ExecutionInfo {
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final CommandArguments args;
}
