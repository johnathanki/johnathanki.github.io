package com.zybooks.inventoryapp.repo;

import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Contains the CRUD methods for the InventoryItem table
 */
public interface InventoryService {

    @GET("/items")
    Call<List<InventoryItem>> getAllItems(@Header("Authorization") String token);

    @GET("/items/id/{id}")
    Call<InventoryItem> getItemById(@Path("id") long id, @Header("Authorization") String token);

    @GET("/items/sku/{sku}")
    Call<InventoryItem> getItemBySku(@Path("sku") String sku, @Header("Authorization") String token);

    @POST("/items")
    Call<InventoryItem> createItem(@Body InventoryItem item, @Header("Authorization") String token);

    @PUT("/items/{id}")
    Call<InventoryItem> updateItem(@Path("id") long id, @Body InventoryItem item, @Header("Authorization") String token);

    @DELETE("/items/{id}")
    Call<Void> deleteItem(@Path("id") long id, @Header("Authorization") String token);
}
