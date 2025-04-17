package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;

import java.util.List;

/**
 * Responsible for managing data related to the Inventory Activity
 */
public class InventoryViewModel {

    private InventoryRepo inventoryRepo;

    public InventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInventoryRepo(application.getApplicationContext());
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryRepo.getItems();
    }

    public void updateInventoryItem(InventoryItem item) {
        inventoryRepo.updateItem(item);
    }

    public void deleteItem(InventoryItem item) {
        inventoryRepo.deleteItem(item);
    }

}
