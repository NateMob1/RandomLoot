package org.nathanlovette.randomloot;

public class util {
    public static boolean stringIsInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
