package com.example.weathertest.ResponseDAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherObjectList {

    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherObjectUnit> list = null;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<WeatherObjectUnit> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherObjectUnit> list) {
        this.list = list;
    }

}


