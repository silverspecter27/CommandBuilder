package com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils;

public final class StringUtils {

    private StringUtils() {
    }

    /**
     * test if the string is boolean if yes
     * get the boolan value of the string
     *
     * @param s the string that will be tested
     * @return true if the string is true
     */
    public static boolean getParseBoolean(String s) {
        if (!(s.equals("true") || s.equals("false")))
            throw new IllegalArgumentException();
        return s.equals("true");
    }

    /**
     * test if the string is boolean if yes
     * get the boolan value of the string
     *
     * @param s the string that will be
     *          tested ignoring the case
     * @return true if the string is true
     */
    public static boolean getParseBooleanEqualsIgnoreCase(String s) {
        if (!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")))
            throw new IllegalArgumentException();
        return s.equals("true");
    }

    /**
     * Returns a string resulting from replacing all occurrences of
     * {@code oldChar} in this string with {@code newChar}.
     * <p>
     * If the character {@code oldChar} does not occur in the
     * character sequence represented by this {@code String} object,
     * then a reference to this {@code String} object is returned.
     * Otherwise, a {@code String} object is returned that
     * represents a character sequence identical to the character sequence
     * represented by this {@code String} object, except that every
     * occurrence of {@code oldChar} is replaced by an occurrence
     * of {@code newChar}.
     * <p>
     * Examples:
     * <blockquote><pre>
     * "mesquite in your cellar".replace('e', 'o')
     *         returns "mosquito in your collar"
     * "the war of baronets".replace('r', 'y')
     *         returns "the way of bayonets"
     * "sparring with a purple porpoise".replace('p', 't')
     *         returns "starring with a turtle tortoise"
     * "JonL".replace('q', 'x') returns "JonL" (no change)
     * </pre></blockquote>
     *
     * @param   str the string that will be replaced.
     * @param   oldChar   the old character.
     * @param   newChar   the new character.
     * @return  a string derived from this string by replacing every
     *          occurrence of {@code oldChar} with {@code newChar}.
     */
    public static String replace(String str, char oldChar, char newChar) {
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (array[i] == oldChar) ? newChar : array[i];
        }
        return new String(array);
    }

    /**
     * test if the string is in lower case
     *
     * @param s the string that will be tested
     * @return true if the string is in lowerCase
     */
    public static boolean isInLowerCase(String s) {
        return s.equals(s.toLowerCase());
    }

    /**
     * test if the string is in upper case
     *
     * @param s the string that will be tested
     * @return true if the string is in upperCase
     */
    public static boolean isInUpperCase(String s) {
        return s.equals(s.toUpperCase());
    }
}
