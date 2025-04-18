package com.zybooks.inventoryapp.repo;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Contains the CRUD methods for the Account table
 */
public interface AccountService {

    @GET("/account/{username}")
    Call<Account> getAccountByUsername(@Path("username") String username);

    @POST("/account")
    Call<Void> registerAccount(@Body Account account);

    @PUT("/account/{username}")
    Call<Void> updateAccount(@Path("username") String username, @Body Account account, @Header("Authorization") String token);

    @DELETE("/account/{username}")
    Call<Void> deleteAccount(@Path("username") String username, @Header("Authorization") String token);

    @POST("/account/login")
    Call<LoginResponse> login(@Body Account request);
}
