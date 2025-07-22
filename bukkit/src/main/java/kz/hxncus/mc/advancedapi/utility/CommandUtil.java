package kz.hxncus.mc.advancedapi.utility;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@UtilityClass
public final class CommandUtil {
	private final Logger log = LoggerFactory.getLogger(CommandUtil.class);
    @Getter
    private final CommandMap commandMap = ReflectionUtil.getFieldValue("commandMap", Bukkit.getServer());
    @Getter
    private final Map<String, Command> knownCommands = ReflectionUtil.getFieldValue("knownCommands", CommandUtil.getCommandMap());
    @Getter
    private final List<String> myCommands = new ArrayList<>();

    /**
     * Получить команду по названию
     * @param commandName название команды
     * @return команду
     */
    public Command getCommand(String commandName) {
        return CommandUtil.knownCommands.get(commandName);
    }

    public void updatePlayersCommands() {
        Bukkit.getServer().getOnlinePlayers().forEach(Player::updateCommands);
    }

    /**
     * Получить алиасы команды
     * @param name название команды
     * @param inclusive включать ли название команды в алиасы
     * @return алиасы команды
     */
    @NonNull
    public Set<String> getAliases(@NonNull String name, boolean inclusive) {
        return CommandUtil.getAliases(CommandUtil.getCommand(name), inclusive);
    }
    
    /**
     * Получить алиасы команды
     * @param command команда
     * @param inclusive включать ли название команды в алиасы
     * @return алиасы команды
     */
    @NonNull
    public Set<String> getAliases(@NonNull Command command, boolean inclusive) {
        Set<String> aliases = new HashSet<>(command.getAliases());
        if (inclusive) {
            aliases.add(command.getName());
        }
        return aliases;
    }

    /**
     * Получить алиасы команды
     * @param name название команды
     * @return алиасы команды
     */
    @NonNull
    public Set<String> getAliases(@NonNull String name) {
        return CommandUtil.getAliases(name, false);
    }

    /**
     * Удалить команду
     * @param commandName имя команды, которую надо удалить
     */
    public void unregisterCommand(String commandName) {
        Command command = CommandUtil.getCommand(commandName);
        if (command == null) {
            log.warn("Command not found: {}", commandName);
            return;
        }
        CommandUtil.unregisterCommand(command);
    }
    
    /**
     * Зарегистировать команду
     * @param command регистрируемая команда
     */
    public void registerCommand(Command command, boolean registerPlayerCommands) {
        CommandUtil.unregisterCommand(command);

        Set<String> aliases = CommandUtil.getAliases(command, true);
        aliases.forEach(alias -> {
            CommandUtil.knownCommands.put(alias.toLowerCase(), command);
            CommandUtil.myCommands.add(alias.toLowerCase());
        });
        if (registerPlayerCommands) {
            updatePlayersCommands();
        }
    }

    /**
     * Удалить команду
     * @param command команда, которую надо удалить
     */
    public void unregisterCommand(Command command) {
        command.getAliases().forEach(alias -> {
            CommandUtil.knownCommands.remove(alias.toLowerCase());
            CommandUtil.myCommands.remove(alias.toLowerCase());
        });
    }

    /**
     * Удалить команду по классу
     * @param commandClass класс команды
     */
    public void unregisterCommand(Class<? extends Command> commandClass) {
        CommandUtil.knownCommands.values().removeIf(commandClass::isInstance);
    }

    /**
     * Удалить все команды плагина
     */
    public void unregisterMyCommands() {
        CommandUtil.myCommands.forEach(CommandUtil::unregisterCommand);
        CommandUtil.myCommands.clear();
    }
}
