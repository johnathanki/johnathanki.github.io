package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.AddInventoryViewModel;

import java.util.Date;

/**
 * Activity for adding a new InventoryItem to the database.
 */
public class AddInventoryActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private TextView itemNameErrorTextView;

    private EditText itemDescEditText;
    private TextView itemDescErrorTextView;

    private EditText itemQuantityEditText;
    private TextView itemQuantityErrorTextView;

    private EditText itemPriceEditText;
    private TextView itemPriceErrorTextView;

    private EditText itemSkuEditText;
    private TextView itemSkuErrorTextView;

    private Button confirmButton;
    private Button cancelButton;

    private AddInventoryViewModel inventoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        inventoryViewModel = new AddInventoryViewModel(getApplication());
        itemNameEditText = findViewById(R.id.new_item_name);
        itemNameEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemNameErrorTextView = findViewById(R.id.item_name_error);

        itemDescEditText = findViewById(R.id.new_item_desc);
        itemDescEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemDescErrorTextView = findViewById(R.id.item_desc_error);

        itemQuantityEditText = findViewById(R.id.new_item_quantity);
        itemQuantityEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemQuantityErrorTextView = findViewById(R.id.item_quantity_error);

        itemPriceEditText = findViewById(R.id.new_item_price);
        itemPriceEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemPriceErrorTextView = findViewById(R.id.item_price_error);

        itemSkuEditText = findViewById(R.id.new_item_sku);
        itemSkuEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemSkuErrorTextView = findViewById(R.id.item_sku_error);

        confirmButton = findViewById(R.id.confirm_add_item);
        confirmButton.setOnClickListener(this::onClick);
        cancelButton = findViewById(R.id.cancel_add_item);
        cancelButton.setOnClickListener(this::onClick);

    }

    /**
     * Handles button presses for the two buttons in this activity. Either attempts to add a new item
     * or cancel and return to the InventoryActivity activity.
     * @param view the parent view
     */
    private void onClick(View view) {
        if (view.getId() == R.id.confirm_add_item) {
            addNewInventoryItem();
        } else { // Cancel button
            Intent intent = new Intent(AddInventoryActivity.this, InventoryActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Attempts to add a new InventoryItem to the database. Will fail if the provided SKU already exists
     * in the database.
     */
    private void addNewInventoryItem() {
        String name = itemNameEditText.getText().toString();
        String desc = itemDescEditText.getText().toString();
        int quantity = Integer.parseInt(itemQuantityEditText.getText().toString());
        double price = Double.parseDouble(itemPriceEditText.getText().toString());
        String sku = itemSkuEditText.getText().toString();
        if (inventoryViewModel.checkSkuExists(sku)) {
            itemSkuErrorTextView.setText(getString(R.string.error,
                    "SKU already exists in database"), TextView.BufferType.NORMAL);
            itemSkuErrorTextView.setVisibility(View.VISIBLE);
            return;
        }

        Date date = new Date();
        InventoryItem item = new InventoryItem(name, desc, quantity, price, sku, date, date);

        inventoryViewModel.addNewInventoryItem(item);
        Intent intent = new Intent(AddInventoryActivity.this, InventoryActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back arrow press
        if (item.getItemId() == android.R.id.home) {
            // Navigate up to the parent or a specific activity if needed
            Intent intent = new Intent(this, InventoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * TextWatcher implementation for the TextViews of this activity. Will prevent the confirm button
     * from being pressed if all text fields are not populated.
     */
    private class AddInventoryTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            boolean canConfirm = itemNameEditText.getText().length() > 0
                    && itemDescEditText.getText().length() > 0
                    && itemQuantityEditText.getText().length() > 0
                    && itemPriceEditText.getText().length() > 0
                    && itemSkuEditText.getText().length() > 0;
            confirmButton.setEnabled(canConfirm);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
