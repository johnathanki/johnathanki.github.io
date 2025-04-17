const Item = require('../model/inventory_item');

// Controller to execute CRUD functions for items

// Add a new item
exports.createItem = function(req, res) {
    const newItem = {
        itemName: req.body.itemName,
        itemDescription: req.body.itemDescription,
        quantity: req.body.quantity,
        price: req.body.price,
        sku: req.body.sku,
        dateAdded: req.body.dateAdded,
        dateUpdated: req.body.dateUpdated
    };

    Item.createItem(newItem, (err, result) => {
        if (err)
            throw err;
        res.json({ message: 'Item created', result});
    });
};

// Fetch all items
exports.getAllItems = function(req, res) {
    Item.getAllItems((err, items) => {
        if (err)
            throw err;
        res.json(items);
    });
};

// Fetch a single item by ID
exports.getItemById = function(req, res) {
    Item.getItemById(req.params.id, (err, item) => {
        if (err)
            throw err;
        res.json(item);
    });
};

// Count the number of  times a SKU appears
exports.getSkuCount = function(req, res) {
    Item.getSkuCount(req.params.sku, (err, count) => {
        if (err)
            throw err;
        res.json(count);
    });
};

// Update a single item
exports.updateItem = function(req, res) {
    Item.updateItem(req.params.id, req.body,
        (err, result) => {
            if (err)
                throw err;
            res.json({ message: 'Item updated'});
        }
    );
};

// Remove a single item
exports.deleteItem = function(req, res) {
    Item.deleteItem(req.params.id, (err, result) => {
        if (err)
            throw err;
        res.json({ message: 'Item deleted'});
    });
};