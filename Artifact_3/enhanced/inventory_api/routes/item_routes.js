const express = require('express');
const router = express.Router();
const itemController = require('../controller/item_controller');
const auth = require('../middleware/authentication');

// Router for item endpoints
router.get('/', auth, itemController.getAllItems);
router.get('/id/:id', auth, itemController.getItemById);
router.get('/sku/:sku', auth, itemController.getSkuCount);
router.post('/', auth, itemController.createItem);
router.put('/:id', auth, itemController.updateItem);
router.delete('/:id', auth, itemController.deleteItem);

module.exports = router;
