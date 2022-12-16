package com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Register;

import com.github.silverspecter27.commandbuilder.CommandBuilder.Register.CommandBase;
import com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils.CommandUtils;
import com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils.Message.MessageBuilder;
import com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils.Message.Msg;
import com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * use this class to register a command
 * and execute all you need
 */
public abstract class CommandRegister extends CommandBase {

    private final int minArguments;
    private final int maxArguments;

    private final boolean playerOnly;

    private final Plugin plugin;

    private Set<Player> permittedPlayers = null;

    private Set<Player> delayedPlayers = null;
    private long delay = 0;

    public CommandRegister(Plugin plugin, @NotNull String command) {
        super(command);

        if (plugin == null) {
            plugin = JavaPlugin.getProvidingPlugin(this.getClass());
        }

        this.plugin = plugin;

        Method method = null;
        try {
            method = this.getClass().getMethod("onCommand", CommandSender.class, Command.class, String.class, String[].class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        CommandDescription commandDescription = null;
        if (method == null) {
            for (Method methods : getClass().getDeclaredMethods()) {
                CommandDescription annotation = methods.getAnnotation(CommandDescription.class);
                if (annotation != null) {
                    commandDescription = annotation;
                    break;
                }
            }
        } else {
            commandDescription = method.getAnnotation(CommandDescription.class);
        }

        //this.setUsage(command);

        if (commandDescription == null) {
            this.minArguments = 0;
            this.maxArguments = -1;

            this.playerOnly = false;
        } else {
            this.setDescription(commandDescription.desc());
            this.setAliases(Arrays.asList(commandDescription.aliases()));

            this.minArguments = commandDescription.min();
            this.maxArguments = commandDescription.max();

            this.playerOnly = commandDescription.playerOnly();
        }

        try {
            CommandUtils.register(plugin, this);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the value of minimum arguments for the command work
     */
    public final int getMinArguments() {
        return minArguments;
    }

    /**
     * @return the value of maximum arguments for the command work
     */
    public final  int getMaxArguments() {
        return maxArguments;
    }

    /**
     * @return true if the command only work with players
     */
    public final boolean isPlayerOnly() {
        return playerOnly;
    }

    /**
     * test if the command require permission.
     * @return true if the command require permission.
     */
    public final boolean hasPermission() {
        return permittedPlayers != null;
    }

    /**
     * enable the usage only for player that have permission.
     */
    @NotNull
    public final CommandRegister enablePermission() {
        this.permittedPlayers = new HashSet<>();
        return this;
    }

    /**
     * add the player to the list of permittedPlayers.
     * @throws NullPointerException if the permittedPlayers set is null.
     * @param target the player that will be added.
     */
    public final void setPermission(Player target) {
        if (!hasPermission())
            throw new NullPointerException("The permittedPlayers set is null");
        this.permittedPlayers.add(target);
    }

    /**
     * remove the player from the list of permittedPlayers.
     * @param player the player that will be removed.
     * @throws NullPointerException if the permittedPlayers set is null.
     */
    public final void removePermission(Player player) {
        if (!hasPermission())
            throw new NullPointerException("The permittedPlayers set is null");
        this.permittedPlayers.remove(player);
    }

    /**
     * test if the player has permission to use this command.
     * @param player the player that will be tested.
     * @throws NullPointerException if the permittedPlayers set is null.
     * @return true if the player has permission.
     */
    public final boolean isPlayerPermitted(Player player) {
        if (!hasPermission())
            throw new NullPointerException("The permittedPlayers set is null");
        return permittedPlayers.contains(player);
    }

    /**
     * test if the command has delay.
     * @return true if the command has delay.
     */
    public final boolean hasDelay() {
        return delayedPlayers != null;
    }

    /**
     * enable the delay for who use the command.
     * @param delay ticks (1 tick = 1 second / 20) that will delay.
     */
    @NotNull
    public final CommandRegister enableDelay(long delay) {
        this.delay = delay;
        this.delayedPlayers = new HashSet<>();
        return this;
    }

    /**
     * remove the player from the list of delayedPlayers.
     * @throws NullPointerException if the delayedPlayers set is null.
     * @param player the player that will be removed.
     */
    public final void removePlayer(Player player) {
        if (!hasDelay())
            throw new NullPointerException("The delayedPlayers set is null");
        this.delayedPlayers.remove(player);
    }

    /**
     * send the usage message to the player.
     * @param sender - the user that will receive the usage.
     */
    public void sendUsage(CommandSender sender) {
        Msg.send(sender, getUsage());
    }

    private String[] args;

    /**
     * Executes the command, returning its success.
     *
     * @param sender Source object which is executing this command.
     * @param commandLabel The alias of the command used.
     * @param args All arguments passed to the command, split via ' '.
     * @return true if the command was successful, otherwise false.
     */
    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.args = args;

        if (args.length < minArguments) {
            Msg.send(sender, "&cUnknown or incomplete command, see below for error \n&7" +
                    commandLabel + MessageBuilder.message(1, "&c&n", args) + "&c&o<--[HERE]");
            return true;
        }

        if (args.length > maxArguments && maxArguments > -1) {
            Msg.send(sender, "&cIncorrect argument for command \n&7" +
                    commandLabel + MessageBuilder.message(1 , maxArguments, args) + MessageBuilder.message(maxArguments + 1, "&c&n", args) + "&c&o<--[HERE]");
            return true;
        }

        if (playerOnly && !(sender instanceof Player)) {
            Msg.send(sender, "&cOnly players can use this command.");
            return true;
        }

        if (permittedPlayers != null &&
                (sender instanceof Player && !permittedPlayers.contains(sender) && !sender.isOp()))
        {
            Msg.send(sender, "&cYou don't have permission to use this command.");
            return true;
        }

        if (delayedPlayers != null && sender instanceof Player) {
            Player player = (Player) sender;
            if (delayedPlayers.contains(player)) {
                Msg.send(sender, "&cPlease wait before using this command again.");
                return true;
            }

            delayedPlayers.add(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> removePlayer(player), delay);
        }

        if (!onCommand(sender, this, commandLabel, args)) {
            sendUsage(sender);
        }

        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     *     command inside a command block, this will be the player, not
     *     the command block.
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args The arguments passed to the command, including final
     *     partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     *     to default to the command executor
     * @note: it doesn't need to be overridden.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

    /**
     * get the argument at the given position.
     * Ex: label arg1 arg2.
     *
     * @param position the position of the argument at the command.
     * @return the expected argument.
     */
    @NotNull
    public final String getArg(int position) {
        if (args.length < position || position < 1)
            throw new IllegalArgumentException();
        return args[position - 1];
    }

    /**
     * get the integer value that is at the argument value.
     *
     * @param argument the argument which will be got the value.
     * @return the value of the parseInt.
     * @throws NumberFormatException if the player failure wrote the integer value.
     */
    protected int getParseInteger(int argument) throws NumberFormatException {
        argument = setInArgBounds(argument);
        int i = 0;
        if (args.length >= argument)
            i = Integer.parseInt(args[argument - 1]);
        return i;
    }

    /**
     * get the double value that is at the argument value.
     *
     * @param argument the argument which will be got the value.
     * @return the value of the parseDouble.
     * @throws NumberFormatException if the player failure wrote the double value.
     */
    protected double getParseDouble(int argument) throws NumberFormatException {
        argument = setInArgBounds(argument);
        double d = 0;
        if (args.length >= argument)
            d = Double.parseDouble(args[argument - 1]);
        return d;
    }

    /**
     * get the boolean value that is at the argument value.
     *
     * @param argument the argument which will be got the argument.
     * @return the value of the parseBoolean.
     * @throws IllegalArgumentException if the player failure wrote the boolean value.
     */
    protected boolean getParseBoolean(int argument) {
        argument = setInArgBounds(argument);
        boolean b = false;
        if (args.length >= argument)
            b = StringUtils.getParseBoolean(args[argument - 1]);
        return b;
    }

    /**
     * if the value is not in argument bounds it will be
     * replaced with the nearest value in argument bounds.
     *
     * @param value the value that will be tested.
     * @return the value in bounds.
     */
    protected int setInArgBounds(int value) {
        if (!isInArgBounds(value))
            value = (value > maxArguments && maxArguments > -1) ? maxArguments : minArguments;
        return value;
    }

    /**
     * test if the value is in argument bounds.
     *
     * @param value the value that will be tested.
     * @return true if the value is in bounds.
     */
    protected boolean isInArgBounds(int value) {
        return (maxArguments < 0 || value <= maxArguments) && value >= minArguments;
    }
}
