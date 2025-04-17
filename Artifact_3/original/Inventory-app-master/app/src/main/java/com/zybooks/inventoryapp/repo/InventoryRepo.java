package com.zybooks.inventoryapp.repo;

import android.content.Context;

import androidx.room.Room;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.List;

/**
 * Responsible for managing the Account and Inventory databases
 */
public class InventoryRepo {

    private final InventoryDao inventoryDao;
    private final AccountDao accountDao;

    private static InventoryRepo inventoryRepo;


    public static InventoryRepo getInventoryRepo(Context context) {
        if (inventoryRepo == null)
            inventoryRepo = new InventoryRepo(context);
        return inventoryRepo;
    }

    private InventoryRepo(Context context) {
        InventoryDatabase database = Room.databaseBuilder(context, InventoryDatabase.class,
                "inventory.db").allowMainThreadQueries().build();
        inventoryDao = database.inventoryDao();
        accountDao = database.accountDao();

        if (inventoryDao.getInventory().isEmpty())
            addSampleItems();
    }

    private void addSampleItems() {
        InventoryItem item = new InventoryItem("Bottled Water 12Floz", "Bottled water",
                12);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Paper napkins 100ct", "Disposable papar napkins",
                50);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Silverware 6pk", "Assorted silverware",
                22);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Plastic cups 50ct", "Clear plastic cups",
                100);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Paper plates 30ct", "Biodegradable paper plates",
                75);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Trash bags 20ct", "Heavy-duty trash bags",
                40);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Food storage containers", "Set of 10 containers",
                30);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Aluminum foil", "Reynolds wrap aluminum foil",
                60);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Plastic wrap", "Clear plastic wrap",
                25);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Bamboo skewers 100ct", "Natural bamboo skewers",
                80);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Napkin rings 12ct", "Decorative napkin rings",
                50);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Serving utensils", "Set of 3 serving spoons",
                15);
        inventoryDao.addInventoryItem(item);
        item = new InventoryItem("Tablecloth", "Disposable tablecloth 54x108 inches",
                20);
        inventoryDao.addInventoryItem(item);
    }

    public List<InventoryItem> getItems() {
        return inventoryDao.getInventory();
    }

    public void addItem(InventoryItem item) {
        inventoryDao.addInventoryItem(item);
    }

    public void updateItem(InventoryItem item) {
        inventoryDao.updateInventoryItem(item.getQuantity(), item.getId());
    }

    public void deleteItem(InventoryItem item) {
        inventoryDao.deleteInventoryItem(item);
    }

    public void addAccount(Account account) {
        accountDao.addAccount(account);
    }

    public Account getAccount(String username) {
        return accountDao.getAccount(username);
    }
}
