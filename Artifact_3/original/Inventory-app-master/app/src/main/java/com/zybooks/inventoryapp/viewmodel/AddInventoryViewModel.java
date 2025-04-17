package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;

/**
 * Responsible for managing data related to the AddInventory Activity
 */
public class AddInventoryViewModel {

    private InventoryRepo inventoryRepo;

    public AddInventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInventoryRepo(application.getApplicationContext());
    }

    public void addNewInventoryItem(InventoryItem item) {
        inventoryRepo.addItem(item);
    }
}
