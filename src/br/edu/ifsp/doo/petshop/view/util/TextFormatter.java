package br.edu.ifsp.doo.petshop.view.util;

public class TextFormatter {
    public static String formatCpf(String string) {
        if (string.length() != 11)
            return string;

        return string.substring(0, 2) + "." + string.substring(3, 5) + "." + string.substring(6, 8) + "-" + string.substring(9, 10);
    }

    public static String formatMoney(Double d) {
        String[] strings = d.toString().split(".");

        if (strings.length != 2)
            return strings[0] + "," + strings[1];

        return d.toString();
    }

    public static String formatMoney(String s) {
        String[] strings = s.split(".");

        if (strings.length != 2)
            return strings[0] + "," + strings[1];

        return s;
    }
}
