package com.github.silverspecter27.commandbuilder.Utils.Message;

import org.jetbrains.annotations.NotNull;

public final class MessageBuilder {

    private MessageBuilder() {
    }

    //show args

    /**
     * Build a message that have the string array <args> of constructor
     *
     * @param argValue the argument value that will be selected.
     * @param args the array of arguments.
     * @return the message built with the arguments.
     */
    @NotNull
    public static String message(int argValue, @NotNull String[] args) {
        return message(argValue, args.length, "", args);
    }

    /**
     * Build a message that have the string array <args> of constructor
     *
     * @param argValue the argument value that will be selected.
     * @param maxArgValue the argument value that will end.
     * @param args the array of arguments.
     * @return the message built with the arguments.
     */
    @NotNull
    public static String message(int argValue, int maxArgValue, @NotNull String[] args) {
        return message(argValue, maxArgValue, "", args);
    }

    /**
     * Build a message that have the string array <args> of constructor
     *
     * @param color the color that will be replaced.
     * @param args the array of arguments.
     * @return the message built with the arguments.
     */
    public static String message(@NotNull String color, @NotNull String[] args) {
        return message(1, color, args);
    }

    /**
     * Build a message that have the string array <args> of constructor
     *
     * @param argValue the argument value that will be selected.
     * @param color the color that will be replaced.
     * @param args the array of arguments.
     * @return the message built with the arguments.
     */
    public static String message(int argValue, @NotNull String color, @NotNull String[] args) {
        return message(argValue, args.length, color, args);
    }

    /**
     * Build a message that have the string array <args> of constructor
     *
     * @param argValue the argument value that will be selected.
     * @param maxArgValue the argument value that will end.
     * @param color the color that will be replaced.
     * @param args the array of arguments.
     * @return the message built with the arguments.
     */
    public static String message(int argValue, int maxArgValue, @NotNull String color, @NotNull String[] args) {
        StringBuilder message = new StringBuilder();
        for (; argValue <= maxArgValue; argValue++) {
            message.append(" ").append(color).append(args[argValue - 1]);
        }
        return message.toString();
    }
}
