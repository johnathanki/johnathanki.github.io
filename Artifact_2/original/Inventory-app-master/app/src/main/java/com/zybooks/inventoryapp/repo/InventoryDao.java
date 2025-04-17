package com.zybooks.inventoryapp.repo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.List;

@Dao
public interface InventoryDao {

    @Query("SELECT * FROM InventoryItem WHERE id=:id")
    InventoryItem getInventoryItem(long id);

    @Query("SELECT * FROM InventoryItem")
    List<InventoryItem> getInventory();

    @Insert()
    void addInventoryItem(InventoryItem item);

    @Query("UPDATE inventoryitem SET quantity=:quantity WHERE id=:id")
    void updateInventoryItem(int quantity, long id);

    @Delete
    void deleteInventoryItem(InventoryItem item);


}
