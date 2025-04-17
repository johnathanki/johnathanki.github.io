package com.zybooks.inventoryapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class detailing the structure for the InventoryItem SQLite table
 */
@Entity
public class InventoryItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @NonNull
    @ColumnInfo(name="itemName")
    private String itemName;

    @NonNull
    @ColumnInfo(name="itemDescription")
    private String itemDescription;

    @ColumnInfo(name="quantity")
    private int quantity;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemDescription(@NonNull String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @NonNull
    public String getItemDescription() {
        return itemDescription;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public InventoryItem(@NonNull String itemName, @NonNull String itemDescription, int quantity) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
    }
}
