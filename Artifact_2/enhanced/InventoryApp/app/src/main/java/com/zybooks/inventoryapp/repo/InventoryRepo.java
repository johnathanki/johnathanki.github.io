package com.zybooks.inventoryapp.repo;

import android.content.Context;

import androidx.room.Room;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.Calendar;
import java.util.Date;
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

    /**
     * Collects all InventoryItems into a List
     * @return the list of InventoryItems
     */
    public List<InventoryItem> getItems() {
        return inventoryDao.getInventory();
    }

    /**
     * Adds an InventoryItem into the database
     * @param item the InventoryItem to be added
     */
    public void addItem(InventoryItem item) {
        inventoryDao.addInventoryItem(item);
    }

    /**
     * Updates an existing InventoryItem in the database
     * @param item the InventoryItem to be updated
     */
    public void updateItem(InventoryItem item) {
        Date date = new Date();
        inventoryDao.updateInventoryItem(item.getQuantity(), item.getId(), date);
    }

    /**
     * Deletes an InventoryItem from the database
     * @param item the item to be deleted
     */
    public void deleteItem(InventoryItem item) {
        inventoryDao.deleteInventoryItem(item);
    }

    /**
     * Adds a new Account to the database
     * @param account the Account to be added
     */
    public void addAccount(Account account) {
        accountDao.addAccount(account);
    }

    /**
     * Searches the database for an Account based on username
     * @param username the username to search for
     * @return the Account with a matching username, otherwise null
     */
    public Account getAccount(String username) {
        return accountDao.getAccount(username);
    }

    /**
     * Counts how many SKU codes match the provided sku in the database. Used to determine if a SKU
     * is unique
     * @param sku the SKU to search for
     * @return the number of times that SKU code is present in the database
     */
    public int getSkuCount(String sku) {
        return inventoryDao.getSkuCount(sku);
    }

    /**
     * Adds dummy InventoryItems into the database for testing purposes
     */
    private void addSampleItems() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2025, Calendar.MARCH, 1);
        InventoryItem item = new InventoryItem("Bottled Water 12Floz", "Bottled water",
                12, 5.99, "SKU001", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 2);
        item = new InventoryItem("Paper napkins 100ct", "Disposable paper napkins",
                50, 3.49, "SKU002", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 3);
        item = new InventoryItem("Silverware 6pk", "Assorted silverware",
                22, 7.99, "SKU003", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 4);
        item = new InventoryItem("Plastic cups 50ct", "Clear plastic cups",
                100, 4.25, "SKU004", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 5);
        item = new InventoryItem("Paper plates 30ct", "Biodegradable paper plates",
                75, 6.75, "SKU005", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 6);
        item = new InventoryItem("Trash bags 20ct", "Heavy-duty trash bags",
                40, 8.50, "SKU006", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 7);
        item = new InventoryItem("Food storage containers", "Set of 10 containers",
                30, 12.99, "SKU007", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 8);
        item = new InventoryItem("Aluminum foil", "Reynolds wrap aluminum foil",
                60, 3.89, "SKU008", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 9);
        item = new InventoryItem("Plastic wrap", "Clear plastic wrap",
                25, 2.99, "SKU009", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 10);
        item = new InventoryItem("Bamboo skewers 100ct", "Natural bamboo skewers",
                80, 4.49, "SKU010", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 11);
        item = new InventoryItem("Napkin rings 12ct", "Decorative napkin rings",
                50, 9.99, "SKU011", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 12);
        item = new InventoryItem("Serving utensils", "Set of 3 serving spoons",
                15, 6.25, "SKU012", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);

        calendar.set(2025, Calendar.MARCH, 13);
        item = new InventoryItem("Tablecloth", "Disposable tablecloth 54x108 inches",
                20, 4.75, "SKU013", calendar.getTime(), calendar.getTime());
        inventoryDao.addInventoryItem(item);
    }
}
