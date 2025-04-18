package com.zybooks.inventoryapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zybooks.inventoryapp.repo.util.DateAdapter;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class obtains the Retrofit client instance responsible for making calls to the backend API
 *
 * @author John Kirven
 */
public class ApiClient {
    private static Retrofit retrofit = null;

    /**
     * Construct and/or return a Retrofit client using the Retrofit Builder
     * and a Gson converter factory for Long->Date conversions.
     * @return the current Retrofit client
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.183:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
