package com.zybooks.inventoryapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class detailing the structure of the Account SQLite table
 */
@Entity
public class Account {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="username")
    private String username;

    @NonNull
    @ColumnInfo(name="password")
    private String password;

    /**
     * Constructs a new Account with the specified username and password
     * @param username the Accounts username
     * @param password the Accounts password
     */
    public Account(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sets a new username for the account
     * @param username the new username
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Sets a new password for the account
     * @param password the new password
     */
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

}
