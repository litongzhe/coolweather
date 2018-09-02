package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/8/31.
 */

public class Utility {
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                int length = allProvinces.length();
                JSONObject provinceObject;
                for(int i = 0; i < length; i++){
                    Province province = new Province();
                    provinceObject = allProvinces.getJSONObject(i);
                    province.setProvinceName(provinceObject.getString("name"));
                    Log.d("省份：",province.getProvinceName());
                    province.setProvinceCode(provinceObject.getInt("id"));
                    Log.d("id：",String.valueOf(province.getProvinceCode()));
                    province.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                JSONObject cityObject;
                int length = allCities.length();
                for(int i = 0; i < length; i++){
                    City city = new City();
                    cityObject = allCities.getJSONObject(i);
                    city.setCityName(cityObject.getString("name"));
//                    Log.d("name:", city.getCityName());
                    city.setCityCode(cityObject.getInt("id"));
//                    Log.d("id",String.valueOf(city.getCityCode()));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleCountyResponse(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                JSONObject countyObject;
                int length = allCounties.length();
                for(int i = 0; i < length; i++){
                    County county = new County();
                    countyObject = allCounties.getJSONObject(i);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
