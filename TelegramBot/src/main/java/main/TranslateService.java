package main;

import com.darkprograms.speech.translator.GoogleTranslate;

import java.io.IOException;

public class TranslateService {
    public static String getTranslatedText(String text) {
        String result;
        try {
            result = GoogleTranslate.translate(Constants.TARGET_LANGUAGE, text);
        } catch (IOException e) {
            result = Constants.ERROR_WHILE_TRANSLATING;
        }
        return result;
    }
}
