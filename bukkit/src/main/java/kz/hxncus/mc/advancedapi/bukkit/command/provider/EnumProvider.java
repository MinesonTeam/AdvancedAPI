package kz.hxncus.mc.advancedapi.bukkit.command.provider;

import kz.hxncus.mc.advancedapi.api.bukkit.command.provider.TabProvider;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumProvider<T extends Enum<T>> implements TabProvider {

    private final Class<T> enumClass;

    public EnumProvider(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public List<String> provide(CommandSender sender) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
