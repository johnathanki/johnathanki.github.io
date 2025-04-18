package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;

/**
 * Responsible for managing data related to the AddInventory Activity
 */
public class AddInventoryViewModel {

    private InventoryRepo inventoryRepo;

    /**
     * Creates an inventory repository for a given Application
     * @param application the application to attach to
     */
    public AddInventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInventoryRepo(application.getApplicationContext());
    }

    /**
     * Add an InventoryItem object into the database via the inventory repository
     * @param item the InventoryItem to add
     */
    public void addNewInventoryItem(InventoryItem item) {
        inventoryRepo.addItem(item);
    }

    /**
     * Counts the number of times a given SKU appears in the database
     * @param sku the SKU to search for
     * @return the number of times the SKU appears
     */
    public int getSkuCount(String sku) {
        return inventoryRepo.getSkuCount(sku);
    }


}
