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

    /**
     * Looks for an Account with a username matching the search criteria
     * @param username the username to look for
     * @return an Account with a matching username, otherwise null
     */
    public Account getAccount(String username) {
        return inventoryRepo.getAccount(username);
    }

    /**
     * Adds a new Account via the inventory repository
     * @param account the Account to be added
     */
    public void addAccount(Account account) {
        inventoryRepo.addAccount(account);
    }

    /**
     * Checks to see if an Accounts username and password match the supplied username and password
     * @param username the username to compare
     * @param password the password to compare
     * @return true if the username and password match an Account, otherwise false
     */
    public boolean validLogin(String username, String password) {
        Account account = getAccount(username);
        if (account == null)
            return false;
        return account.getPassword().equalsIgnoreCase(password);
    }
}
