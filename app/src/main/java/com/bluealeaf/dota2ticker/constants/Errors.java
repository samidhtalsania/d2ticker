package com.bluealeaf.dota2ticker.constants;

/**
 * Created by samidh on 10/1/15.
 */
public class Errors {
    public static final String Cache_IO_Error = "Unable to create cache";
    public static final String Retrofit_error = "Unable to fetch data now. Please try again later.";

    //Retrofit Specific Errors
    public static final String Retrofit_NETWORK = "No network found. Please connect to internet.";
    public static final String Retrofit_HTTP = "Unable to connect to server. Please try again later.";
    public static final String Retrofit_CONVERSION = "Unable to convert.";
    public static final String Retrofit_UNEXPECTED = "Unexpected error.";
}
