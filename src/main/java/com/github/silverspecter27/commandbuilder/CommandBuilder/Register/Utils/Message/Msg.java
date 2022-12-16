package com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils.Message;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Msg {

    private Msg() {
    }

    //pre-programmed the build of the message import to use it

    /**
     * @param sender the selected entity that will receive the message.
     * @param message the message that will be sent.
     * send a message to a player
     */
    public static void send(CommandSender sender, String message) {
        send(sender, message, "&f");
    }

    /**
     * @param sender the selected entity that will receive the message.
     * @param message the message that will be sent.
     * send a message to a player
     */
    public static void send(CommandSender sender, String message, String prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(int message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(double message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(short message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(long message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(char message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void send(String message) {
        Bukkit.getConsoleSender().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(int message) {
        Bukkit.broadcast(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(double message) {
        Bukkit.broadcast(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(short message) {
        Bukkit.broadcast(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(long message) {
        Bukkit.broadcast(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(char message) {
        Bukkit.broadcast(Component.text(message));
    }

    /**
     * @param message the message that will be sent.
     * send a message to the console.
     */
    public static void sendAll(String message) {
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
