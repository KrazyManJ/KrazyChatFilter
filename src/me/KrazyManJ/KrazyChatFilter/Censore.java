package me.KrazyManJ.KrazyChatFilter;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Censore {
    public static String Message(String message){
        if (Main.getInstance().getConfig().getBoolean("flood")) message = flood(message);
        if (Main.getInstance().getConfig().getBoolean("capslock") && message.length() >= Main.getInstance().getConfig().getInt("size of message to capslock")) message = capslock(message);
        if (Main.getInstance().getConfig().getBoolean("ipblock")) message = removeIPs(message);
        for (Pattern regex : Censore.getSwearRegex()) {
            Matcher match = regex.matcher(message);
            while (match.find()) {
                String foundWord = message.substring(match.start(), match.end());
                String replacement = Main.getInstance().getConfig().getBoolean("ignore middle-char in censor") ? foundWord.replaceAll(Objects.requireNonNull(Main.getInstance().getConfig().getString("middle-char symbols")), "") : foundWord;
                message = message.replaceAll(foundWord, Objects.requireNonNull(StringUtils.repeat(Main.getInstance().getConfig().getString("censore symbol"), replacement.length())));
                match = regex.matcher(message);
            }
        }
        return message;
    }

    private static List<Pattern> swearRegex = new ArrayList<>();

    public static void reloadRegex(){
        swearRegex = new ArrayList<>();
        List<String> swearWords = Main.getInstance().getConfig().getStringList("swear words");
        swearWords.sort(Comparator.comparingInt(String::length));
        Collections.reverse(swearWords);
        String middleChar = Main.getInstance().getConfig().getString("middle-char symbols");
        for (String word : swearWords){
            String pattern = "["+String.join(middleChar.replaceAll("[\\[\\] ]", "")+"]+"+middleChar+"*[", word.split(""))+"]+";
            //NUMBERS
            pattern = !Main.getInstance().getConfig().getBoolean("number as letters") ? pattern :
                    pattern.replaceAll("o", "o0");
            //.replaceAll("i", "i1").replaceAll("l", "l1").replaceAll("e", "e3").replaceAll("a", "a4");

            //DIACRITICS
            pattern = !Main.getInstance().getConfig().getBoolean("diacritic") ? pattern :
                    pattern.replaceAll("a", "a??").replaceAll("c", "c??")
                            .replaceAll("d", "d??").replaceAll("e", "e????")
                            .replaceAll("i", "i??").replaceAll("o", "o????")
                            .replaceAll("r", "r??").replaceAll("(?<!\\\\)s", "s??")
                            .replaceAll("t", "t??").replaceAll("u", "u????")
                            .replaceAll("y", "y??").replaceAll("z", "??");
            //IY
            pattern = !Main.getInstance().getConfig().getBoolean("i and y exchange") ? pattern :
                    pattern.replaceAll("i", "yi").replaceAll("i(?!y)", "iy");
            swearRegex.add(Pattern.compile("(?i)"+pattern));
        }
    }
    public static List<Pattern> getSwearRegex(){
        return swearRegex;
    }

    public static String flood(String input){
        Matcher match = Pattern.compile("(?i)(.)\\1{"+(Main.getInstance().getConfig().getInt("flood size")-1)+",}").matcher(input);
        while (match.find()) input = input.replace(match.group(),match.group(1));
        return input;
    }
    public static String capslock(String input){
        int capscount = 0;
        for (int i = 0; i < input.length() ; i++) if (Character.isUpperCase(input.charAt(i))) capscount++;
        if (capscount/(float)input.length()*100 >= Main.getInstance().getConfig().getInt("capslock percentage")) input = input.toLowerCase();
        return input;
    }
    public static String removeIPs(String input){
        Matcher match = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}").matcher(input);
        while (match.find()) {
            String found = input.substring(match.start(),match.end());
            input = input.replace(found, StringUtils.repeat("*", found.length()));
            match = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}").matcher(input);
        }
        return input;
    }
}
