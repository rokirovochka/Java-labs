import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherService {
    private static final String apiKey = "c38c0465255795dfd9ee66f5de5c8012";

    public static String getWeather(String message, WeatherModel weatherModel) throws IOException {
        String city = message.replace(Constants.weatherButtonText, "").replace(" ", "");
        if (message.equals(Constants.weatherButtonText)) {
            city = WeatherModel.getCityByDefault();
        }
        String spec = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=" + apiKey;
        URL url = new URL(spec);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        weatherModel.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        weatherModel.setTemp(main.getDouble("temp"));
        weatherModel.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            weatherModel.setIcon((String) obj.get("icon"));
            weatherModel.setMain((String) obj.get("main"));
        }

        return "City: " + weatherModel.getName() + "\n" +
                "Temperature: " + weatherModel.getTemp() + " C" + "\n" +
                "Humidity: " + weatherModel.getHumidity() + " %" + "\n" +
                "Main: " + weatherModel.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + weatherModel.getIcon() + ".png";
    }
}
