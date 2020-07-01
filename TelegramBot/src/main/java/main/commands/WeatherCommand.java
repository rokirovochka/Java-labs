package main.commands;

import main.Constants;
import main.WeatherModel;
import main.WeatherService;
import org.telegram.telegrambots.api.objects.Message;

import java.io.IOException;

public class WeatherCommand implements Command {

    @Override
    public String execute(Message message, WeatherModel weatherModel) {
        String result;
        try {
            result = WeatherService.getWeather(message.getText(), weatherModel);
        } catch (IOException e) {
            result = Constants.CITY_NOT_FOUND;
        }
        return result;
    }
}
