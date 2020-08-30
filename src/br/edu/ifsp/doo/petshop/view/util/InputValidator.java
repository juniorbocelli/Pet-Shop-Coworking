package br.edu.ifsp.doo.petshop.view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean isNumeric(String input){
        String pattern = "\\d*";
        return applyPattern(pattern, input);
    }

    private static boolean applyPattern(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.find())
            return true;
        else
            return false;
    }

    public static boolean isEmail(String input){
        String pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return applyPattern(pattern, input);
    }

    public static boolean isPhone(String input) {
        String pattern = "^\\(\\d{2}\\)\\d{4}\\-\\d{4}$";
        return applyPattern(pattern, input);
    }

    public static boolean isCell(String input) {
        String pattern = "^(\\(?\\d{2}\\)?\\s?)?(9?\\s?\\d{4}(\\-|\\s)?\\d{4})\\s?(\\(\\d{4}\\))?$";
        return applyPattern(pattern, input);
    }

    public static boolean isCPF(String input){
        String pattern = "([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})$";
        return applyPattern(pattern, input);
    }

    public static boolean isMoney(String input){
        String pattern = "^\\d{1,},\\d{2}$";
        return applyPattern(pattern, input);
    }
}
