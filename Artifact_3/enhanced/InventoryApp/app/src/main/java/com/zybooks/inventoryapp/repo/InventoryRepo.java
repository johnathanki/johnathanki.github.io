package com.zybooks.inventoryapp.repo;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.retrofit.ApiClient;

import java.util.List;

import retrofit2.Callback;

/**
 * Responsible for managing the Account and Inventory databases via InventoryService and
 * AccountService instances that communicate with the backend API
 */
public class InventoryRepo {

    private InventoryService itemApi;
    private AccountService accountApi;

    private static InventoryRepo instance;


    /**
     * Constructs an InventoryRepo instance with an InventoryService instance and an AccountService
     * instance.
     */
    private InventoryRepo() {
        itemApi = ApiClient.getClient().create(InventoryService.class);
        accountApi = ApiClient.getClient().create(AccountService.class);
    }

    /**
     * Retrieves the InventoryRepo singleton instance
     * @return the singleton instance
     */
    public static synchronized InventoryRepo getInstance() {
        if (instance == null) {
            instance = new InventoryRepo();
        }
        return instance;
    }

    /**
     * Asyncronously retrieves all InventoryItems from the API.
     *
     * @param token The JWT authorization token (without "Bearer" prefix).
     * @param callback The Retrofit callback to handle success or failure.
     */
    public void getItems(String token, Callback<List<InventoryItem>> callback) {
        itemApi.getAllItems("Bearer " + token).enqueue(callback);
    }

    /**
     * Sends a new InventoryItem to the backend API to be added.
     *
     * @param item The InventoryItem to add.
     * @param token The JWT token (without "Bearer").
     * @param callback Callback for Retrofit response.
     */
    public void addItem(InventoryItem item, String token, Callback<InventoryItem> callback) {
        itemApi.createItem(item, "Bearer " + token).enqueue(callback);
    }

    /**
     * Sends an update request for an existing InventoryItem to the backend.
     *
     * @param item The item with updated values.
     * @param token The JWT token.
     * @param callback Callback for Retrofit response.
     */
    public void updateItem(InventoryItem item, String token, Callback<InventoryItem> callback) {
        itemApi.updateItem(item.getId(), item, "Bearer " + token).enqueue(callback);
    }

    /**
     * Sends a delete request for the specified InventoryItem.
     *
     * @param item The item to delete.
     * @param token The JWT token.
     * @param callback Callback for Retrofit response.
     */
    public void deleteItem(InventoryItem item, String token, Callback<Void> callback) {
        itemApi.deleteItem(item.getId(), "Bearer " + token).enqueue(callback);
    }

    /**
     * Sends a request to register a new account via the backend API.
     *
     * @param account The account to create.
     * @param callback Callback for Retrofit response.
     */
    public void addAccount(Account account, Callback<Void> callback) {
        accountApi.registerAccount(account).enqueue(callback);
    }

    /**
     * Asynchronously retrieves an account by username.
     *
     * @param username The username to search for.
     * @param callback Callback for Retrofit response.
     */
    public void getAccount(String username, Callback<Account> callback) {
        accountApi.getAccountByUsername(username).enqueue(callback);
    }

    /**
     * Retrieves how many items share the specified SKU from the backend API.
     *
     * @param sku The SKU code to check.
     * @param token The JWT token.
     * @param callback Callback for Retrofit response.
     */
    public void getSkuCount(String sku, String token, Callback<InventoryItem> callback) {
        itemApi.getItemBySku(sku, "Bearer " + token).enqueue(callback);
    }

}
