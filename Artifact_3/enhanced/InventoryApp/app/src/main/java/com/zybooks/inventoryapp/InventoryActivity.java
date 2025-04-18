package com.zybooks.inventoryapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.InventoryViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * The primary activity for this app. This activity is responsible for displaying a List of
 * InventoryItems with various sorting options and options to modify items stock/quantity as well as
 * remove InventoryItems from the database.
 */
public class InventoryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private RecyclerView recyclerView;
    private InventoryViewModel inventoryViewModel;
    String[] sortOptions;
    Map<String, Boolean> sortDirections = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        inventoryViewModel = new InventoryViewModel(getApplication());

        if (!inventoryViewModel.isInventoryLoaded()) {
            inventoryViewModel.loadInventoryItems(() -> {
                List<InventoryItem> items = inventoryViewModel.getInventoryItems();
                updateInterface(items);
            });
        } else {
            updateInterface(inventoryViewModel.getInventoryItems());
        }
        findViewById(R.id.add_item_button).setOnClickListener(view -> addInventoryItem());

        recyclerView = findViewById(R.id.inventory_list);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        sortOptions = getResources().getStringArray(R.array.sort_options);

        // Set up default ascending for each sort option
        for (String s : sortOptions) {
            sortDirections.put(s, true);
        }
    }

    /**
     * Sorts the List of InventoryItems by a specified metric in either ascending or descending order.
     * This newly sorted List is then pushed to the RecyclerView.
     * @param sortType the sort type, e.g. "name" or "quantity"
     * @param descending true for descending order, otherwise ascending
     */
    private void handleSortOptions(String sortType, boolean descending) {
        List<InventoryItem> itemList;
        switch (sortType) {
            case "Name": // Name
                itemList = inventoryViewModel.sortByName(descending);
                break;
            case "Quantity": // Quantity
                itemList = inventoryViewModel.sortByQuantity(descending);
                break;
            case "Price": // Price
                itemList = inventoryViewModel.sortByPrice(descending);
                break;
            case "Date added": // Date added
                itemList = inventoryViewModel.sortByDateAdded(descending);
                break;
            case "Date modified":
                itemList = inventoryViewModel.sortByDateModified(descending);
                break;
            default:
                itemList = inventoryViewModel.getInventoryItems();
                break;
        }
        sortDirections.put(sortType, !descending);

        updateInterface(itemList);
    }

    /**
     * Helper function to remove the arrows present next to sort option names.
     * @param s the sort field to strip
     * @return the field stripped of all arrows
     */
    private String stripExistingArrows(String s) {
        return s.replace(getString(R.string.arrow_up), "")
                .replace(getString(R.string.arrow_down), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            showSortMenu(findViewById(R.id.sort));
        }
        if (item.getItemId() == R.id.enable_sms) {
            checkForSMSPermission();
            return true;
        }
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return true;
    }

    /**
     * Build and display a simple PopupMenu to display options for sorting the List of InventoryItems
     * @param view the parent View
     */
    private void showSortMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        Menu menu = popup.getMenu();

        for (String option : sortOptions) {
            boolean descending = Boolean.TRUE.equals(sortDirections.getOrDefault(option, false));
            String sortArrow = descending ? getString(R.string.arrow_down) : getString(R.string.arrow_up);
            menu.add(option + sortArrow);
        }

        popup.setOnMenuItemClickListener(menuItem -> {
            String sortType = Objects.requireNonNull(menuItem.getTitle()).toString();
            sortType = stripExistingArrows(sortType);
            boolean descending = Boolean.TRUE.equals(sortDirections.getOrDefault(sortType, false));
            handleSortOptions(sortType, descending);
            return true;
        });
        popup.show();
    }

    /**
     * Updates the InventoryAdapter with the current list of inventoryItems
     * @param inventoryItems the inventoryItems from the database
     */
    private void updateInterface(List<InventoryItem> inventoryItems) {
        InventoryAdapter inventoryAdapter = new InventoryAdapter(inventoryItems);
        recyclerView.setAdapter(inventoryAdapter);
    }

    /**
     * Switches the user to the AddInventory Activity
     */
    private void addInventoryItem() {
        Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * ViewHolder for InventoryItems
     */
    private class InventoryHolder extends RecyclerView.ViewHolder {
        private InventoryItem item;
        private final TextView itemNameTextView;
        private final TextView itemPriceTextView;
        private final TextView itemStockTextView;
        private final Button reduceButton;
        private final Button increaseButton;
        private final Button deleteButton;

        public InventoryHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.list_inventory, viewGroup, false));
            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            itemStockTextView = itemView.findViewById(R.id.itemStock);
            reduceButton = itemView.findViewById(R.id.reduceStock);
            reduceButton.setOnClickListener(view -> reduceStock());
            increaseButton = itemView.findViewById(R.id.increaseStock);
            increaseButton.setOnClickListener(view -> increaseStock());
            deleteButton = itemView.findViewById(R.id.deleteItem);
            deleteButton.setOnClickListener(view -> deleteItem());
        }

        /**
         * Deletes the current item from the inventory if the user confirms
         */
        private void deleteItem() {
            AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
            builder.setTitle(R.string.confirm_delete).setMessage(R.string.are_you_sure)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        inventoryViewModel.deleteItem(item);
                        updateInterface(inventoryViewModel.getInventoryItems());
                        update();
                    }).setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss())
                    .show();
        }

        /**
         * Increases the items quantity by 1
         */
        private void increaseStock() {
            int stock = Integer.parseInt(itemStockTextView.getText().toString().substring(5));
            item.setQuantity(stock + 1);
            update();
        }

        /**
         * Decreases the items quantity by 1
         */
        private void reduceStock() {
            int stock = Integer.parseInt(itemStockTextView.getText().toString().substring(5));
            if (stock > 0) {
                item.setQuantity(stock - 1);
                update();
            }
        }

        /**
         * Binds the current item to the view
         * @param item the inventory item
         * @param position the position of the inventory item in the view
         */
        public void bind(InventoryItem item, int position) {
            this.item = item;
            itemNameTextView.setText(item.getItemName(), TextView.BufferType.NORMAL);
            itemPriceTextView.setText(getString(R.string.itemPrice, String.format(Locale.US,
                    "%.2f", item.getPrice())));
            itemStockTextView.setText(getString(R.string.itemStock, String.valueOf(item.getQuantity())));
            if (item.getQuantity() == 0) { // Disable reduce button if quantity is 0
                reduceButton.setEnabled(false);
            }
            if (position % 2 == 0) { // Alternate between background colors
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.bg_even));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.bg_odd));
            }
        }

        /**
         * Update the UI. Determines if buttons should be enabled or disabled.
         */
        private void update() {
            inventoryViewModel.updateInventoryItem(item);
            itemStockTextView.setText(getString(R.string.itemStock, String.valueOf(item.getQuantity())));
            if (item.getQuantity() == 0) {
                reduceButton.setEnabled(false);
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED) {
                    // You can use the API that requires the permission.
                    sendSms(item);
                }
            } else {
                reduceButton.setEnabled(true);
            }
        }
    }


    /**
     * Checks for SMS permissions and request if not granted
     */
    private void checkForSMSPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.SEND_SMS)) {
            showPermissionDenied();
        } else {
            Toast.makeText(getApplicationContext(), "SMS alerts are already enabled",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Asks the user to open their settings to enable SMS permissions
     */
    private void showPermissionDenied() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
        builder.setTitle(R.string.permission_required).setMessage(R.string.permission_ask)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }).setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    /**
     * Sends a SMS message to the user
     * @param item the item that has run out of stock
     */
    private void sendSms(InventoryItem item) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "ALERT: Inventory item \"" + item.getItemDescription() + "\" has run out of stock.";
        smsManager.sendTextMessage("+15551234567", null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }

    private class InventoryAdapter extends RecyclerView.Adapter<InventoryHolder> {

        private final List<InventoryItem> inventoryItemList;

        public InventoryAdapter(List<InventoryItem> items) {
            inventoryItemList = items;
        }

        @NonNull
        @Override
        public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new InventoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
            holder.bind(inventoryItemList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return inventoryItemList.size();
        }
    }
}
