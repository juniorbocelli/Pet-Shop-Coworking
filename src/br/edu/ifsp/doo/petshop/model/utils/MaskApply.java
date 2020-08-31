package br.edu.ifsp.doo.petshop.model.utils;

public class MaskApply {
    public static String maskCfp(String s) {
        if (s.length() != 11)
            return s;

        return s.substring(0, 3) + "." + s.substring(3, 6) + "." + s.substring(6, 9) + "-" + s.substring(9, 11);
    }

    public static String maskPhone(String s) {
        if (s.length() != 10)
            return s;

        return "(" +  s.substring(0, 2) + ")" + s.substring(2, 6) + "-" + s.substring(6, 10);
    }

    public static String maskCell(String s) {
        if (s.length() != 11)
            return s;

        return "(" +  s.substring(0, 2) + ")" + s.substring(2, 7) + "-" + s.substring(7, 11);
    }

    public static String maskMoney(Double d) {
        String[] strings = d.toString().split("\\.");

        if (strings.length == 2)
            if (strings[1].length() < 2)
                return strings[0] + "," + "0" + strings[1];
            else
                return strings[0] + "," + strings[1];

        return d.toString();
    }

    public static String maskMoney(String s) {
        String[] strings = s.split(".");

        if (strings.length != 2)
            return strings[0] + "," + strings[1];

        return s;
    }
}
