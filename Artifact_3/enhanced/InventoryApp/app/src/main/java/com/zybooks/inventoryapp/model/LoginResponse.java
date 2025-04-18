package com.zybooks.inventoryapp.model;

/**
 * This class defines the structure of a simple login request that is sent to the API.
 * Valid requests will return a JSON Web Token.
 *
 * @author John Kirven
 */
public class LoginResponse {

    private String message;
    private String token;

    public String getMessage() { return message; }
    public String getToken() { return token; }

}
