package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;
import com.zybooks.inventoryapp.repo.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Responsible for managing data related to the AddInventory Activity
 */
public class AddInventoryViewModel {

    private final InventoryRepo inventoryRepo;
    private final Context context;
    private String token;

    /**
     * Creates an inventory repository for a given Application
     * @param application the application to attach to
     */
    public AddInventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInstance();
        context = application.getApplicationContext();
        token = TokenManager.getToken(context);

    }

    /**
     * Add an InventoryItem object into the database via the inventory API
     * @param item the InventoryItem to add
     *
     */
    public void addNewInventoryItem(InventoryItem item) {
        inventoryRepo.addItem(item, token, new Callback<InventoryItem>() {
            @Override
            public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InventoryItem> call, Throwable t) {
                Toast.makeText(context, "Add failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Counts the number of times a given SKU appears in the database.
     * If 1 or more, sets skuExists to true
     *
     * @param sku the SKU to search for
     */
    public boolean checkSkuExists(String sku) {
        final boolean[] exists = new boolean[1];
        inventoryRepo.getSkuCount(sku, token, new Callback<InventoryItem>() {
            @Override
            public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exists[0] = false;
                } else {
                    exists[0] = true;
                }
            }

            @Override
            public void onFailure(Call<InventoryItem> call, Throwable t) {
                exists[0] = false;
            }
        });
        return exists[0];
    }
}
