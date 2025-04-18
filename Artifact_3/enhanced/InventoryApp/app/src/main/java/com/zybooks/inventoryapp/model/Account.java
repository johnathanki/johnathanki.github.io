package com.zybooks.inventoryapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Account object detailing the structure of the Account table
 */
public class Account {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    /**
     * Constructs a new Account with the specified username and password
     * @param username the Accounts username
     * @param password the Accounts password
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sets a new username for the account
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username for the account
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a new password for the account
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password for the account
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
