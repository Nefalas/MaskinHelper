package se.nefalas.engine.common.utils;

public class CharUtils {

    public static boolean isOneOf(char c, char[] chars) {
        for (char ch : chars) {
            if (ch == c) {
                return true;
            }
        }

        return false;
    }
}
