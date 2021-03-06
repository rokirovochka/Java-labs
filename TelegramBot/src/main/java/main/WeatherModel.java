package main;

public class WeatherModel {

    private static String cityByDefault = Constants.CITY_BY_DEFAULT;

    private String name;
    private Double temp;
    private Double humidity;
    private String icon;
    private String main;

    public static void setCityByDefault(String cityByDefault) {
        WeatherModel.cityByDefault = cityByDefault;
    }

    public static String getCityByDefault() {
        return cityByDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

}
