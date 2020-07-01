package main.commands;

import main.WeatherModel;
import org.telegram.telegrambots.api.objects.Message;

public interface Command {
    String execute(Message message, WeatherModel weatherModel);
}
