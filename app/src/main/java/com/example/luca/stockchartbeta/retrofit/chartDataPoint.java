package com.example.luca.stockchartbeta.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class chartDataPoint {

    // http://www.jsonschema2pojo.org/

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("open")
    @Expose
    private Float open;

    @SerializedName("high")
    @Expose
    private Float high;

    @SerializedName("low")
    @Expose
    private Float low;

    @SerializedName("close")
    @Expose
    private Float close;

    @SerializedName("volume")
    @Expose
    private Integer volume;

    @SerializedName("unadjustedVolume")
    @Expose
    private Integer unadjustedVolume;

    @SerializedName("change")
    @Expose
    private Double change;

    @SerializedName("changePercent")
    @Expose
    private Double changePercent;

    @SerializedName("vwap")
    @Expose
    private Double vwap;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("changeOverTime")
    @Expose
    private Double changeOverTime;

    /**
     * No args constructor for use in serialization
     */
    public chartDataPoint() {
    }

    /**
     * @param open
     * @param vwap
     * @param changePercent
     * @param change
     * @param unadjustedVolume
     * @param volume
     * @param label
     * @param high
     * @param changeOverTime
     * @param low
     * @param date
     * @param close
     */
    public chartDataPoint(String date, Float open, Float high, Float low, Float close,
                          Integer volume, Integer unadjustedVolume,
                          Double change, Double changePercent, Double vwap,
                          String label, Double changeOverTime) {
        super();
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.unadjustedVolume = unadjustedVolume;
        this.change = change;
        this.changePercent = changePercent;
        this.vwap = vwap;
        this.label = label;
        this.changeOverTime = changeOverTime;
    }

    public String getDate() {
        return date;
    }


    public Float getOpen() {
        return open;
    }


    public Float getHigh() {
        return high;
    }


    public Float getLow() {
        return low;
    }


    public Float getClose() {
        return close;
    }


    public Integer getVolume() {
        return volume;
    }


    public Integer getUnadjustedVolume() {
        return unadjustedVolume;
    }


    public Double getChange() {
        return change;
    }


    public Double getChangePercent() {
        return changePercent;
    }


    public Double getVwap() {
        return vwap;
    }


    public String getLabel() {
        return label;
    }


    public Double getChangeOverTime() {
        return changeOverTime;
    }


}