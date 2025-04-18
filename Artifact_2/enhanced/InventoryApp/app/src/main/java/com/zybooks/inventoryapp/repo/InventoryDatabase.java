package com.zybooks.inventoryapp.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.util.Converters;

@Database(entities = {Account.class, InventoryItem.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class InventoryDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract InventoryDao inventoryDao();
}
