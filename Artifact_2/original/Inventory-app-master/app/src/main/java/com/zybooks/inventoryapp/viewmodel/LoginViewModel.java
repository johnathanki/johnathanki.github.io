package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.repo.InventoryRepo;

/**
 * Responsible for managing data related to the Login Activity
 */
public class LoginViewModel {

    private InventoryRepo inventoryRepo;

    public LoginViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInventoryRepo(application.getApplicationContext());
    }

    public Account getAccount(String username) {
        return inventoryRepo.getAccount(username);
    }

    public void addAccount(Account account) {
        inventoryRepo.addAccount(account);
    }

    public boolean validLogin(String username, String password) {
        Account account = getAccount(username);
        if (account == null)
            return false;
        return account.getPassword().equalsIgnoreCase(password);
    }
}
