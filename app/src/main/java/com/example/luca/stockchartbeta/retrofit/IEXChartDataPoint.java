package com.example.luca.stockchartbeta.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IEXChartDataPoint {

    // http://www.jsonschema2pojo.org/

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("open")
    @Expose
    private Double open;

    @SerializedName("high")
    @Expose
    private Double high;

    @SerializedName("low")
    @Expose
    private Double low;

    @SerializedName("close")
    @Expose
    private Double close;

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
    public IEXChartDataPoint() {
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
    public IEXChartDataPoint(String date, Double open, Double high, Double low, Double close, Integer volume, Integer unadjustedVolume, Double change, Double changePercent, Double vwap, String label, Double changeOverTime) {
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


    public Double getOpen() {
        return open;
    }


    public Double getHigh() {
        return high;
    }


    public Double getLow() {
        return low;
    }


    public Double getClose() {
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