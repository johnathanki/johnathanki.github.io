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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.InventoryViewModel;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private RecyclerView recyclerView;
    private InventoryViewModel inventoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(InventoryActivity.class.toString(), "onCreate() call");
        setContentView(R.layout.activity_inventory);
        inventoryViewModel = new InventoryViewModel(getApplication());
        findViewById(R.id.add_item_button).setOnClickListener(view -> addInventoryItem());

        recyclerView = findViewById(R.id.inventory_list);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        updateInterface(inventoryViewModel.getInventoryItems());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("AddInventoryActivity", "item selected: " + item.getItemId());
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

    private class InventoryHolder extends RecyclerView.ViewHolder {
        private InventoryItem item;
        private final TextView itemNameTextView;
        private final TextView itemStockTextView;
        private final Button reduceButton;
        private final Button increaseButton;
        private final Button deleteButton;

        public InventoryHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.list_inventory, viewGroup, false));
            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemStockTextView = itemView.findViewById(R.id.itemStock);
            reduceButton = itemView.findViewById(R.id.reduceStock);
            reduceButton.setOnClickListener(view -> reduceStock());
            increaseButton = itemView.findViewById(R.id.increaseStock);
            increaseButton.setOnClickListener(view -> increaseStock());
            deleteButton = itemView.findViewById(R.id.deleteItem);
            deleteButton.setOnClickListener(view -> deleteItem());
            if (itemNameTextView != null && itemStockTextView != null)
                Log.d("InventoryActivity", "itemName: " + itemNameTextView.getText() + " stock: " + itemStockTextView.getText());
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
         * Update the UI
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


    // Register permissions callback
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            });

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
