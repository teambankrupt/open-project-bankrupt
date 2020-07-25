package com.example.coreweb.domains.address.models.entities

import com.example.common.exceptions.notfound.NotFoundException
import com.fasterxml.jackson.annotation.JsonIgnore
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Transient

@Embeddable
class LatLng {
    var latitude = 0.0
    var longitude = 0.0
    var altitude = 0.0

    constructor() {}

    constructor(latitude: Double, longitude: Double, altitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        this.altitude = altitude
    }

    companion object {
        @Transient
        @JsonIgnore
        val LAT = "lat"

        @Transient
        @JsonIgnore
        val LNG = "lng"

        @Throws(IOException::class, NotFoundException::class)
        fun parse(area: String?): Map<String, Double> {
            var areaName = area ?: throw NotFoundException("Can not parse LatLng, area isn\'t provided or empty!")
            areaName = areaName.replace(" ", "%20")
            val url = URL("https://maps.googleapis.com/maps/api/geocode/json?address=$areaName,dhaka&sensor=true&key=AIzaSyANZfglkpcpj5QcUHw-zPGloYnpeE4qiMY")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val br: BufferedReader
            br = if (connection.responseCode == 200) BufferedReader(InputStreamReader(connection.inputStream)) else BufferedReader(InputStreamReader(connection.errorStream))
            var output: String?
            val sb = StringBuilder()
            while (br.readLine().also { output = it } != null) {
                sb.append(output)
            }
            br.close()
            output = sb.toString()
            return parseLatLng(output!!)
        }

        @Throws(NotFoundException::class)
        private fun parseLatLng(output: String): Map<String, Double> {
            val jsonArray = JSONObject(output).getJSONArray("results")
            val jsonObject: JSONObject?
            if (jsonArray == null || jsonArray.length() < 1) throw NotFoundException("Can not parse LatLng, area isn\'t provided or empty!")
            jsonObject = (jsonArray[0] as JSONObject).getJSONObject("geometry").getJSONObject("location")
            val latLng: MutableMap<String, Double> = HashMap()
            latLng["lat"] = jsonObject.getDouble("lat")
            latLng["lng"] = jsonObject.getDouble("lng")
            return latLng
        }
    }

    override fun toString(): String {
        return "LatLng{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}'
    }

}