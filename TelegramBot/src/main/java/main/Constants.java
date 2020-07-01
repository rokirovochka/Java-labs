package main;

public final class Constants {
    public static final String HELP_BUTTON_TEXT = "Help" + Emoji.QUESTION_EMOJI;
    public static final String DATE_BUTTON_TEXT = "Date" + Emoji.DATE_EMOJI;
    public static final String TIME_BUTTON_TEXT = "Time" + Emoji.WATCH_EMOJI;
    public static final String WEATHER_BUTTON_TEXT = "Weather";

    public static final String HELP_MESSAGE_TEXT = "*Hello*" + Emoji.SMILEY_EMOJI + " *Here is a list of functions that I can do:*\n\n" +
            "*Date* - I'll say today's date " + Emoji.DATE_EMOJI + "\n\n" +
            "*Time* - I'll say what time is it " + Emoji.ALARM_CLOCK_EMOJI + "\n\n" +
            "*Weather <city>* - Want to know weather in any city around the world?" + Emoji.WEATHER_EMOJI + Emoji.HOT_WEATHER_EMOJI + "\n\n" +
            "*Location <city>* - To set location you want" + "\n\n" +
            "*Translate <text>* - I'll translate any text to russian";


    public static final String CITY_NOT_FOUND = "City not found";
    public static final String CITY_BY_DEFAULT = "Novosibirsk";
    public static final String LOCATION_HAS_CHANGED = "Location has changed";
    public static final String LOCATION_HASNT_CHANGED = "Location hasn't changed, check syntax and try again";
    public static final String ERROR_WHILE_TRANSLATING = "Something went wrong";
    public static final String TARGET_LANGUAGE = "ru";

    public static final String HELP_CHECK_TEXT = "Help❓";
    public static final String DATE_CHECK_TEXT = "Date\uD83D\uDCC5";
    public static final String TIME_CHECK_TEXT = "Time⌚";
    public static final String WEATHER_CHECK_TEXT = "Weather";
    public static final String LOCATION_CHECK_TEXT = "Location";
    public static final String TRANSLATE_CHECK_TEXT = "Translate";

    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String NAME = "name";
    public static final String TEMP = "temp";
    public static final String HUMIDITY = "humidity";
    public static final String WEATHER = "weather";
    public static final String ICON = "icon";
    public static final String MAIN = "main";

    public static final String CITY_TITLE = "City: ";
    public static final String TEMP_TITLE = "Temperature: ";
    public static final String HUMIDITY_TITLE = "Humidity: ";
    public static final String MAIN_TITLE = "Main: ";
    public static final String IMAGE_TITLE = "http://openweathermap.org/img/w/";
    public static final String PNG_EXTENSION = ".png";
    public static final String CELSIUM = " C";
    public static final String PERCENT = " %";
    public static final String EMPTY_STRING = "";
    public static final String ENDL = "\n";
    public static final String QUERY_ADDRESS = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String QUERY_EXTENSION = "&units=metric&appid=";


}
