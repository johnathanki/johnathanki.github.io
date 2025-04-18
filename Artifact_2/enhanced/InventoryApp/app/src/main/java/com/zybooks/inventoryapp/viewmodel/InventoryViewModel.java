package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Responsible for managing data related to the Inventory Activity
 */
public class InventoryViewModel {

    private InventoryRepo inventoryRepo;

    public InventoryViewModel(Application application) {
        inventoryRepo = InventoryRepo.getInventoryRepo(application.getApplicationContext());
    }

    /**
     * Get a List of current items provided by the inventory repository
     * @return a list of InventoryItems
     */
    public List<InventoryItem> getInventoryItems() {
        return inventoryRepo.getItems();
    }

    /**
     * Update an InventoryItem object through the inventory repository
     * @param item the InventoryItem to update
     */
    public void updateInventoryItem(InventoryItem item) {
        inventoryRepo.updateItem(item);
    }

    /**
     * Delete an InventoryItem through the inventory repository
     * @param item the InventoryItem to delete
     */
    public void deleteItem(InventoryItem item) {
        inventoryRepo.deleteItem(item);
    }

    /**
     * Sort all InventoryItems in the inventory repository by their name in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByName(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getItemName));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their quantity in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByQuantity(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        if (descending) {
            itemList.sort(Comparator.comparingInt(InventoryItem::getQuantity).reversed()
                    .thenComparing(InventoryItem::getItemName));
        } else {
            itemList.sort(Comparator.comparingInt(InventoryItem::getQuantity)
                    .thenComparing(InventoryItem::getItemName));
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their price in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByPrice(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        if (descending) {
            itemList.sort(Comparator.comparingDouble(InventoryItem::getPrice).reversed()
                    .thenComparing(InventoryItem::getItemName));
        } else {
            itemList.sort(Comparator.comparingDouble(InventoryItem::getPrice)
                    .thenComparing(InventoryItem::getItemName));
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their date added in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByDateAdded(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getDateAdded));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }

    /**
     * Sort all InventoryItems in the inventory repository by their date modified in either ascending or
     * descending order
     * @param descending true to sort in descending order, otherwise ascending
     * @return the sorted List of InventoryItems
     */
    public List<InventoryItem> sortByDateModified(boolean descending) {
        List<InventoryItem> itemList = getInventoryItems();
        itemList.sort(Comparator.comparing(InventoryItem::getDateAdded));
        if (descending) {
            Collections.reverse(itemList);
        }
        return itemList;
    }
}
