const db = require('../config/database');

// Create
exports.createItem = function(newItem, callback) {
    db.query('INSERT INTO InventoryItem SET ?', newItem, callback);
};

// Read
exports.getAllItems = function(callback) {
    db.query('SELECT * FROM InventoryItem', callback);
};
exports.getItemById = function(id, callback) {
    db.query('SELECT * FROM InventoryItem WHERE id = ?',
         [id], callback);
};
exports.getSkuCount = function(sku, callback) {
    db.query('SELECT COUNT(*) FROM InventoryItem WHERE sku = ?',
        [sku], callback);
};

// Update
exports.updateItem = function(id, updatedItem, callback) {
    db.query(`UPDATE InventoryItem SET
            itemName = ?,
            itemDescription = ?,
            quantity = ?,
            price = ?,
            sku = ?,
            dateAdded = ?,
            dateUpdated = ?
            WHERE id = ?;`, 
        [updatedItem.itemName,
            updatedItem.itemDescription,
            updatedItem.quantity,
            updatedItem.price,
            updatedItem.sku,
            updatedItem.dateAdded,
            updatedItem.dateUpdated,
            id], callback);
}

// Delete
exports.deleteItem = function(id, callback) {
    db.query('DELETE FROM InventoryItem WHERE id = ?', [id], callback);
}
