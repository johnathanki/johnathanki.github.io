package com.zybooks.inventoryapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * InventoryItem object detailing the structure for the InventoryItem table
 */
public class InventoryItem {

    @SerializedName("id")
    private long id;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("itemDescription")
    private String itemDescription;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    @SerializedName("sku")
    private String itemSku;

    @SerializedName("dateAdded")
    private Date dateAdded;

    @SerializedName("dateUpdated")
    private Date dateUpdated;

    /**
     * Constructs a new InventoryItem with the provided data
     * @param itemName the items name
     * @param itemDescription the items description
     * @param quantity the items quantity
     * @param price the items price
     * @param itemSku the unique SKU
     * @param dateAdded the date added
     * @param dateUpdated the date last modified
     */
    public InventoryItem(String itemName, String itemDescription, int quantity,
                         double price, String itemSku, Date dateAdded, Date dateUpdated) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.price = price;
        this.itemSku = itemSku;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    /**
     * Set the unique ID for an item
     * @param id the unique ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the items unique ID
     * @return the unique ID
     */
    public long getId() {
        return id;
    }

    /**
     * Set an items name
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    /**
     * Set the items description
     * @param itemDescription the item description
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Updates the items quantity
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the quantity of an item
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the price of an item
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the price of an item
     * @return the items price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the SKU for the item
     * @param itemSku the new SKU
     */
    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    /**
     * Get the items SKU
     * @return the items SKU
     */
    public String getItemSku() {
        return itemSku;
    }

    /**
     * Set the added date for an item
     * @param dateAdded the new added date
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Get the date an item was added
     * @return the date the item was added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Set the date the item was last modified
     * @param dateUpdated the date the item was modified
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * Get the date an item was last modified
     * @return the date the item was modified
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

}
