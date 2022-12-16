package com.github.silverspecter27.commandbuilder.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class CommandUtils {

    private CommandUtils() {
    }

    /**
     * get the CommandMap for registration.
     * @return CommandMap.
     */
    public static CommandMap getCommandMap()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field fCommandMap = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
        fCommandMap.setAccessible(true);

        Object commandMapObject = fCommandMap.get(Bukkit.getPluginManager());
        if (commandMapObject instanceof CommandMap) {
            return (CommandMap) commandMapObject;
        }

        return null;
    }

    /**
     * transform a Command into a PluginCommand.
     * @param plugin an instance of the main class.
     * @param command a class that extends the class Command.
     * @return a PluginCommand with the same data of the given command.
     */
    public static PluginCommand getPluginCommand(Plugin plugin, Command command)
            throws IllegalAccessException, NoSuchMethodException,
                    InvocationTargetException, InstantiationException
    {
        Constructor<PluginCommand> init = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        init.setAccessible(true);

        PluginCommand inject = init.newInstance(command.getName(), plugin);
        
        inject.setExecutor((who, __, label, input) -> command.execute(who, label, input));

        inject.setAliases(command.getAliases());
        inject.setDescription(command.getDescription());
        inject.setLabel(command.getLabel());
        inject.setName(command.getName());
        inject.setPermission(command.getPermission());
        inject.setPermissionMessage(command.getPermissionMessage());
        inject.setUsage(command.getUsage());
        return inject;
    }

    /**
     * register the command into the plugin.
     * @param plugin an instance of the main class.
     * @param command a class that extends the class Command.
     */
    public static void register(Plugin plugin, @NotNull Command command)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException
    {
        if (plugin == null) {
            plugin = JavaPlugin.getProvidingPlugin(command.getClass());
        }

        PluginCommand inject = getPluginCommand(plugin, command);

        if (command instanceof TabCompleter) {
            inject.setTabCompleter((who, label, args, input) ->
                    ((TabCompleter) command).onTabComplete(who, label, args, input)
            );
        }

        Objects.requireNonNull(getCommandMap()).register(plugin.getName().toLowerCase(), inject);
    }

    /**
     * unregister all the commands located at the instance location.
     * @param location the class where the commands are located.
     * @throws NullPointerException if the getCommandMap() method return null.
     */
    public static void unregisterAll(Object location) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        CommandMap commandMap = Objects.requireNonNull(getCommandMap());
        for (Command command : ReflectionUtils.getObjects(location, Command.class)) {
            command.unregister(commandMap);
        }
    }
}
