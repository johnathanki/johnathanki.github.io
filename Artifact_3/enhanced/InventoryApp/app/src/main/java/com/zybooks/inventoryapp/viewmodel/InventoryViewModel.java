package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;
import com.zybooks.inventoryapp.repo.util.TokenManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Responsible for managing data related to the Inventory Activity
 */
public class InventoryViewModel {

    private final InventoryRepo inventoryRepo;
    private final Context context;
    private final String token;

    private List<InventoryItem> inventoryCache = new ArrayList<>();

    public InventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInstance();
        context = application.getApplicationContext();
        token = TokenManager.getToken(context);

    }

    /**
     * Load items from the API into the local cache.
     * Should be called once at startup or when refreshing.
     */
    public void loadInventoryItems(Runnable onLoaded) {
        inventoryRepo.getItems(token, new Callback<List<InventoryItem>>() {
            @Override
            public void onResponse(Call<List<InventoryItem>> call, Response<List<InventoryItem>> response) {
                if (response.isSuccessful()) {
                    inventoryCache = response.body();
                    onLoaded.run(); // Notify the UI that items are ready
                } else {
                    Toast.makeText(context, "Failed to load inventory", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                Toast.makeText(context, "Failed to load inventory", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Get a List of current items provided by the inventory API
     * @return a list of InventoryItems
     */
    public List<InventoryItem> getInventoryItems() {
        return inventoryCache;
    }

    /**
     * Update an InventoryItem object through the inventory API
     * @param item the InventoryItem to update
     */
    public void updateInventoryItem(InventoryItem item) {
        inventoryRepo.updateItem(item, token, new Callback<InventoryItem>() {
            @Override
            public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < inventoryCache.size(); i++) {
                        if (inventoryCache.get(i).getId() == item.getId()) {
                            inventoryCache.set(i, item);
                            break;
                        }
                    }
                    Toast.makeText(context, "Item updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InventoryItem> call, Throwable t) {
                Toast.makeText(context, "Update error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Delete an InventoryItem through the inventory API
     * @param item the InventoryItem to delete
     */
    public void deleteItem(InventoryItem item) {
        inventoryRepo.deleteItem(item, token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    inventoryCache.removeIf(i -> i.getId() == item.getId());
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Delete error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sort all InventoryItems in the inventory repository by their name in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByName(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getItemName));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their quantity in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByQuantity(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        if (descending) {
            itemList.sort(Comparator.comparingInt(InventoryItem::getQuantity).reversed()
                    .thenComparing(InventoryItem::getItemName));
        } else {
            itemList.sort(Comparator.comparingInt(InventoryItem::getQuantity)
                    .thenComparing(InventoryItem::getItemName));
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their price in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByPrice(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        if (descending) {
            itemList.sort(Comparator.comparingDouble(InventoryItem::getPrice).reversed()
                    .thenComparing(InventoryItem::getItemName));
        } else {
            itemList.sort(Comparator.comparingDouble(InventoryItem::getPrice)
                    .thenComparing(InventoryItem::getItemName));
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their date added in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByDateAdded(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getDateAdded));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their date modified in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByDateModified(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getDateAdded));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }

    public boolean isInventoryLoaded() {
        return !inventoryCache.isEmpty();
    }
}
