package org.example.textingquest.utils;


import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class TextFormatter {

    public static String formatTextWithQuotes(String text) {
        // Регулярное выражение для поиска цитат
        String regex = "([\\p{L} ]+): «([^»]+)»";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        StringBuilder formattedText = new StringBuilder();

        int lastEnd = 0;
        while (matcher.find()) {
            // Добавляем текст до начала цитаты
            formattedText.append(text, lastEnd, matcher.start());
            String character = matcher.group(1);
            String quote = matcher.group(2);
            formattedText.append("<p style='margin: 10px 0; font-style: italic;'>")
                    .append(character).append(": ").append(quote)
                    .append("</p>");
            lastEnd = matcher.end();
        }
        // Добавляем оставшуюся часть текста после последней цитаты
        formattedText.append(text.substring(lastEnd));

        return formattedText.toString();
    }
}
