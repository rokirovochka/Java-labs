import com.darkprograms.speech.translator.GoogleTranslate;

import java.io.IOException;

public class TranslateService {
    public static String getTranslatedText(String text) {
        String result;
        try {
            result = GoogleTranslate.translate(Constants.targetLanguage, text);
        } catch (IOException e) {
            result = Constants.errorWhileTranslating;
        }
        return result;
    }
}
