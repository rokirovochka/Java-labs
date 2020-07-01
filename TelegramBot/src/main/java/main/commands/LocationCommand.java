package main.commands;

import main.Constants;
import main.WeatherModel;
import main.WeatherService;
import org.telegram.telegrambots.api.objects.Message;

import java.io.IOException;

public class LocationCommand implements Command {

    @Override
    public String execute(Message message, WeatherModel weatherModel) {
        String result;
        try {
            WeatherService.getWeather(message.getText().replace(Constants.LOCATION_CHECK_TEXT, Constants.EMPTY_STRING), weatherModel);
            result = Constants.LOCATION_HAS_CHANGED;
            WeatherModel.setCityByDefault(message.getText().replace(Constants.LOCATION_CHECK_TEXT, Constants.EMPTY_STRING).replace(" ", ""));
        } catch (IOException e) {
            result = Constants.LOCATION_HASNT_CHANGED;
        }
        return result;
    }
}
