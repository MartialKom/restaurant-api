package com.groupe1.restaurant.utils;

public class RestaurantLoginGenerator {

    public static String generateLogin(String name, Integer id){

        String restaurantName = name.trim();

        return restaurantName.substring(0,4)+"-"+String.format("%05d", id);
    }
}
