package com.zybooks.inventoryapp.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.InventoryItem;

@Database(entities = {Account.class, InventoryItem.class}, version = 1, exportSchema = false)
public abstract class InventoryDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract InventoryDao inventoryDao();
}
