package com.example.weathertest.ResponseDAO;

public class WeatherCity {

        private String nameCity;
        private String temp;
        private String imageCity;
        private String windCity;
        private String preasureCity;

    public String getWindCity() {
        return windCity;
    }

    public void setWindCity(String windCity) {
        this.windCity = windCity;
    }

    public String getPreasureCity() {
        return preasureCity;
    }

    public void setPreasureCity(String preasureCity) {
        this.preasureCity = preasureCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getImageCity() {
        return imageCity;
    }

    public void setImageCity(String imageCity) {
        this.imageCity = imageCity;
    }

    public WeatherCity(String nameCity, String temp, String imageCity, String windCity, String preasureCity) {
            this.nameCity = nameCity;
            this.temp = temp;
            this.imageCity = imageCity;
            this.windCity=windCity;
            this.preasureCity=preasureCity;
    }
}
