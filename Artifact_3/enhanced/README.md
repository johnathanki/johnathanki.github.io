# Enhanced Inventory Management App (Android Application + RESTful API + MySQL Database)
This enhanced version of the inventory management app significantly enhances the data storage and backend communication with the following features:
- MySQL database for persistent storage
- Node.js/Express RESTful API with CRUD operations for users and inventory items
- API calls within the App using Retrofit
This version also incorporates the sorting options previously introduced in [The Algorithms and Data Structures Enhancement](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_3/enhanced/)


### Project Structure
The Android app is contained within `InventoryApp/` directory. The RESTful API is contained within the `inventory_api/` directory.


### Requirements
- Android Studio
- Android SDK (28 or higher)
- Node.js (v16 or higher)
- MySQL server (v5.7 or higher)


### Configuration
Create a `.env` file in the `inventory_api` directory with the following variables:
```
PORT=3000
DB_HOST=your_host_url
DB_DATABASE=your_mysql_database
DB_USER=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret
```
Note, the JWT_SECRET should be never be shared and should be strong and unpredictable.

For development/testing, a JWT_SECRET can be generated using the Node.js `crypto` module.
```JS
require('crypto').randomBytes(64).toString('hex')
```


### API testing
All routes are located within the two files in the `inventory_api/routes/` directory. Each endpoint was tested using `Postman` both with and without a valid JWT token to ensure functionality.


### API Integration
This app utilizes `Retrofit` to handle HTTP requests to the API and parsed using `Gson`, including custom support for `Date` conversions from `Long` timestamps.

The Retrofit client is implemented in [`com.zybooks.inventoryapp.retrofit.ApiClient`](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_3/enhanced/InventoryApp/app/src/main/java/com/zybooks/inventoryapp/retrofit/ApiClient.java). To change the URL or port, modify the implementation in `getClient`.
```Java
    /**
     * Construct and/or return a Retrofit client using the Retrofit Builder
     * and a Gson converter factory for Long->Date conversions.
     * @return the current Retrofit client
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("<your-url-here>:<port>/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
```


A narrative for this enhancement can be found here: [CS-499 Milestone Four Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_3/CS-499%20Milestone%20Four%20Narrative.docx)