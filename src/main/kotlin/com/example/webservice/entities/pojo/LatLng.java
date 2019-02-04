package com.example.webservice.entities.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.webservice.exceptions.notfound.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Embeddable
public class LatLng {
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    @Transient
    private LatLngMeta meta;

    @Transient
    @JsonIgnore
    public static final String LAT = "lat";
    @Transient
    @JsonIgnore
    public static final String LNG = "lng";

    public LatLng() {
    }


    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public static Map<String, Double> parse(String areaName) throws IOException, NotFoundException {
        if (areaName == null) throw new NotFoundException("Can not parse LatLng, area isn\'t provided or empty!");
        areaName = areaName.replace(" ","%20");
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + areaName + ",dhaka&sensor=true&key=AIzaSyANZfglkpcpj5QcUHw-zPGloYnpeE4qiMY");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br;
        if (connection.getResponseCode() == 200)
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        else
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        String output;
        StringBuilder sb = new StringBuilder();
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        br.close();
        output = sb.toString();
        return LatLng.parseLatLng(output);
    }

    private static Map<String, Double> parseLatLng(String output) throws NotFoundException {
        JSONArray jsonArray = new JSONObject(output).getJSONArray("results");
        JSONObject jsonObject = null;
        if (jsonArray == null || jsonArray.length() < 1)
            throw new NotFoundException("Can not parse LatLng, area isn\'t provided or empty!");
        jsonObject = ((JSONObject) jsonArray.get(0)).getJSONObject("geometry").getJSONObject("location");


        Map<String, Double> latLng = new HashMap<>();
        latLng.put("lat", jsonObject.getDouble("lat"));
        latLng.put("lng", jsonObject.getDouble("lng"));
        return latLng;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public LatLngMeta getMeta() {
        return meta;
    }

    public void setMeta(LatLngMeta meta) {
        this.meta = meta;
    }
}
