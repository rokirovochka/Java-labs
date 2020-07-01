package main.commands;

import main.DateService;
import main.WeatherModel;
import org.telegram.telegrambots.api.objects.Message;

public class DateCommand implements Command {

    @Override
    public String execute(Message message, WeatherModel weatherModel) {
        return DateService.getDate();
    }
}
