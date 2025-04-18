package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.LoginResponse;
import com.zybooks.inventoryapp.repo.AccountService;
import com.zybooks.inventoryapp.repo.InventoryRepo;
import com.zybooks.inventoryapp.repo.util.TokenManager;
import com.zybooks.inventoryapp.retrofit.ApiClient;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Responsible for managing data related to the Login Activity
 */
public class LoginViewModel {

    private final InventoryRepo inventoryRepo;
    private final Context context;
    private final AccountService accountService;
    public LoginViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInstance();
        context = application.getApplicationContext();
        accountService = ApiClient.getClient().create(AccountService.class);
    }

    /**
     * Sends a registration request to the backend.
     *
     * @param account  the new account to register
     * @param onSuccess the Runnable callback that is called if the request succeeds
     * @param onFailure the Runnable callback that is called if the request fails
     */
    public void addAccount(Account account, Runnable onSuccess, Runnable onFailure) {
        accountService.registerAccount(account).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show();
                    onSuccess.run();
                } else {
                    Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show();
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    /**
     * Attempt to fetch an account with a username matching the provided username. If an account is
     * found, the Consumer boolean is set to true, otherwise it is set to false.
     *
     * @param username the username to look for
     * @param exists the Consumer boolean that marks if the account was found or not
     */
    public void getAccount(String username, Consumer<Boolean> exists) {
        accountService.getAccountByUsername(username).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exists.accept(true);
                } else {
                    exists.accept(false);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                exists.accept(false);
            }
        });
    }

    /**
     * Checks to see if an Accounts username and password match the supplied username and password
     *
     * @param username the username to compare
     * @param password the password to compare
     */
    public void validLogin(String username, String password, Runnable onSuccess, Runnable onFailure) {
        Account account = new Account(username, password);
        accountService.login(account).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    TokenManager.saveToken(context, token);
                    onSuccess.run();
                } else {
                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFailure.run();
            }
        });
    }
}
