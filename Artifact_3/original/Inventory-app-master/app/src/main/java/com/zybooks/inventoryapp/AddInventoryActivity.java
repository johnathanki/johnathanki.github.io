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

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.AddInventoryViewModel;

public class AddInventoryActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private EditText itemDescEditText;
    private EditText itemQuantityEditText;
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
        itemDescEditText = findViewById(R.id.new_item_desc);
        itemDescEditText.addTextChangedListener(new AddInventoryTextWatcher());
        itemQuantityEditText = findViewById(R.id.new_item_quantity);
        itemQuantityEditText.addTextChangedListener(new AddInventoryTextWatcher());
        confirmButton = findViewById(R.id.confirm_add_item);
        confirmButton.setOnClickListener(this::onClick);
        cancelButton = findViewById(R.id.cancel_add_item);
        cancelButton.setOnClickListener(this::onClick);

    }

    private void onClick(View view) {
        if (view.getId() == R.id.confirm_add_item) {
            // Confirm button only works when all fields have content
            // So no need to validate values here
            String name = itemNameEditText.getText().toString();
            String desc = itemDescEditText.getText().toString();
            int quantity = Integer.parseInt(itemQuantityEditText.getText().toString());
            InventoryItem item = new InventoryItem(name, desc, quantity);
            inventoryViewModel.addNewInventoryItem(item);
            Log.d(AddInventoryActivity.class.toString(), "Adding new item to database... name: "
                    + item.getItemName() + " desc: " + item.getItemDescription() + " qty: " + item.getQuantity());
        }
        // Return to inventory list regardless of button pressed
        Intent intent = new Intent(AddInventoryActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("AddInventoryActivity", "item selected: " + item.getItemId());
        // Handle the back arrow press
        if (item.getItemId() == android.R.id.home) {
            // Navigate up to the parent or a specific activity if needed
            Intent intent = new Intent(this, InventoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AddInventoryTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            boolean canConfirm = itemNameEditText.getText().length() > 0
                    && itemDescEditText.getText().length() > 0
                    && itemQuantityEditText.getText().length() > 0;
            confirmButton.setEnabled(canConfirm);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
