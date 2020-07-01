package main.commands;

import main.Constants;
import main.TranslateService;
import main.WeatherModel;
import org.telegram.telegrambots.api.objects.Message;

public class TranslateCommand implements Command {

    @Override
    public String execute(Message message, WeatherModel weatherModel) {
        return TranslateService.getTranslatedText(message.getText().replaceFirst(Constants.TRANSLATE_CHECK_TEXT, Constants.EMPTY_STRING));
    }
}
