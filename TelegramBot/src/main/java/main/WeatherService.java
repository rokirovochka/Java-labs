package main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherService {
    private static final String API_KEY = "c38c0465255795dfd9ee66f5de5c8012";

    public static String getWeather(String message, WeatherModel weatherModel) throws IOException {
        String city = message.replace(Constants.WEATHER_BUTTON_TEXT, Constants.EMPTY_STRING).replace(" ", "");
        if (message.equals(Constants.WEATHER_BUTTON_TEXT)) {
            city = WeatherModel.getCityByDefault();
        }
        String spec = Constants.QUERY_ADDRESS + city + Constants.QUERY_EXTENSION + API_KEY;
        URL url = new URL(spec);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = Constants.EMPTY_STRING;
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        weatherModel.setName(object.getString(Constants.NAME));

        JSONObject main = object.getJSONObject(Constants.MAIN);
        weatherModel.setTemp(main.getDouble(Constants.TEMP));
        weatherModel.setHumidity(main.getDouble(Constants.HUMIDITY));

        JSONArray getArray = object.getJSONArray(Constants.WEATHER);
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            weatherModel.setIcon((String) obj.get(Constants.ICON));
            weatherModel.setMain((String) obj.get(Constants.MAIN));
        }

        return Constants.CITY_TITLE + weatherModel.getName() + Constants.ENDL +
                Constants.TEMP_TITLE + weatherModel.getTemp() + Constants.CELSIUM + Constants.ENDL +
                Constants.HUMIDITY_TITLE + weatherModel.getHumidity() + Constants.PERCENT + Constants.ENDL +
                Constants.MAIN_TITLE + weatherModel.getMain() + Constants.ENDL +
                Constants.IMAGE_TITLE + weatherModel.getIcon() + Constants.PNG_EXTENSION;
    }
}
