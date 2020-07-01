package main.commands;

import main.Constants;
import main.WeatherModel;
import org.telegram.telegrambots.api.objects.Message;

public class HelpCommand implements Command {

    @Override
    public String execute(Message message, WeatherModel weatherModel) {
        return Constants.HELP_MESSAGE_TEXT;
    }
}
