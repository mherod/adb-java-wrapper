package co.herod.adbwrapper.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static boolean isNotEmpty(String s) {
        return !s.trim().isEmpty();
    }

    @NotNull
    public static Integer[] stringArrayToIntArray(String[] strings) {
        Integer[] integers = new Integer[4];
        for (int i = 0; i < 4; i++) {
            integers[i] = Integer.parseInt(strings[i]);
        }
        return integers;
    }

    @NotNull
    static String appendCloseTagIfNotExists(String s) {
        if (!s.endsWith(">")) s += ">";
        return s;
    }

    @NotNull
    public static String[] splitCsv(String s1) {
        return s1.split(",");
    }

    @NotNull
    public static String[] splitKeyValue(String s) {
        return s.trim().split("=", 2);
    }

    public static boolean containsKeyValueSeparator(String s) {
        return s.contains("=");
    }

    public static boolean containsTab(String s) {
        return s.contains("\t");
    }

    @NotNull
    public static String extractXmlString(String s) {
        return s.substring(s.indexOf('<'), (s.lastIndexOf('>') + 1));
    }

    @NotNull
    static List<String> splitOnCloseTag(String s) {
        return Arrays.asList(s.split(">"));
    }
}
