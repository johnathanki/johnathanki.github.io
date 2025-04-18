package com.zybooks.inventoryapp.repo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.Date;
import java.util.List;

@Dao
public interface InventoryDao {

    @Query("SELECT * FROM InventoryItem WHERE id=:id")
    InventoryItem getInventoryItem(long id);

    @Query("SELECT * FROM InventoryItem")
    List<InventoryItem> getInventory();

    @Query("SELECT COUNT(*) FROM InventoryItem WHERE sku = :sku")
    int getSkuCount(String sku);

    @Insert()
    void addInventoryItem(InventoryItem item);

    @Query("UPDATE inventoryitem SET quantity=:quantity, dateUpdated=:date WHERE id=:id")
    void updateInventoryItem(int quantity, long id, Date date);

    @Delete
    void deleteInventoryItem(InventoryItem item);


}
