package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.inventoryapp.model.Account;
import com.zybooks.inventoryapp.viewmodel.LoginViewModel;

/**
 * Primary Activity for this app, will prompt the user to log in or create an account
 * @author John Kirven
 */
public class LoginActivity extends AppCompatActivity {

    // Element used in this layout
    private TextView headerTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView infoTextView;
    private Button loginButton;
    private Button createAccountButton;
    private boolean creatingAccount = false;

    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view to main layout
        setContentView(R.layout.activity_login);

        // Get all the Views for each element
        headerTextView = findViewById(R.id.headerText);
        usernameEditText = findViewById(R.id.usernameInput);
        passwordEditText = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        infoTextView = findViewById(R.id.infoText);
        usernameEditText.addTextChangedListener(new LoginTextWatcher());
        passwordEditText.addTextChangedListener(new LoginTextWatcher());
        loginButton.setOnClickListener(view -> onLoginClick());
        createAccountButton.setOnClickListener(view -> onCreateClick());
        loginViewModel = new LoginViewModel(getApplication());
    }

    /**
     * Called whenever the top button is pressed in the Login Activity. Will attempt the create an
     * account if the user is creating an account and will attempt the login if the user is logging in.
     */
    private void onLoginClick() {
        if (usernameEditText == null || passwordEditText == null)
            return;
        // Attempt login if user is not creating an account
        if (!creatingAccount) {
            String user = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Log.d(LoginActivity.class.toString(), "Attempting login... user: " + user
                    + ", pass: " + password);
            boolean validLogin = loginViewModel.validLogin(user, password);
            if (!validLogin) {
                infoTextView.setText(getString(R.string.error, "Invalid username or password"),
                        TextView.BufferType.NORMAL);
                infoTextView.setVisibility(View.VISIBLE);
                return;
            }
            Intent intent = new Intent(LoginActivity.this, InventoryActivity.class);
            startActivity(intent);
            finish();

        } else { // Attempt to add a new user is user is creating an account
            String user = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (loginViewModel.getAccount(user) != null) {
                infoTextView.setText(getString(R.string.error, "Username already exists"),
                        TextView.BufferType.NORMAL);
                infoTextView.setVisibility(View.VISIBLE);
                return;
            }
            if (!validPassword(password)) {
                infoTextView.setText(getString(R.string.error, "Password should be at least 6 characters"),
                        TextView.BufferType.NORMAL);
                infoTextView.setVisibility(View.VISIBLE);
                return;
            }
            // Create new account with specified credentials
            Account account = new Account(user, password);
            // Add account to the database
            loginViewModel.addAccount(account);
            // Return user to login screen
            onCreateClick();
        }
    }

    /**
     * Called whenever the bottom button is pressed in the Login Activity. This button is the
     * cancel button on both screens.
     */
    private void onCreateClick() {
        // Clear any text from the username and password fields
        usernameEditText.setText("", TextView.BufferType.NORMAL);
        passwordEditText.setText("", TextView.BufferType.NORMAL);
        // If not already creating an account, setup account creation
        if (!creatingAccount) {
            creatingAccount = true;
            headerTextView.setText(getString(R.string.header, "Create an account"));
            headerTextView.setVisibility(View.VISIBLE);
            loginButton.setText(getText(R.string.create_account));
            createAccountButton.setText(getText(R.string.go_back));
        } else { // Otherwise show normal login screen
            creatingAccount = false;
            headerTextView.setVisibility(View.INVISIBLE);
            loginButton.setText(getText(R.string.login));
            createAccountButton.setText(getText(R.string.create_account));
        }
    }

    /**
     * Checks if a password is strong enough to be used
     * @param password the password
     * @return true if the length is greater than 5, otherwise false
     */
    private boolean validPassword(String password) {
        return password.length() >= 6;
    }

    /**
     * TextWatcher implementation for the Login Activity
     */
    private class LoginTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            loginButton.setEnabled(usernameEditText.getText().length() > 0
                    && passwordEditText.getText().length() > 0);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
