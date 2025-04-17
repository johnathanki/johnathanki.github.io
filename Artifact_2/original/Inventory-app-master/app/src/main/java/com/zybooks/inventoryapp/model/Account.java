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

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
    @NonNull
    public String getUsername() {
        return username;
    }
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
    @NonNull
    public String getPassword() {
        return password;
    }

    public Account(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
