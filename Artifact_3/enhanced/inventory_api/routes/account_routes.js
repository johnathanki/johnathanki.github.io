const express = require('express');
const router = express.Router();
const accountController = require('../controller/account_controller');
const auth = require('../middleware/authentication');

// Router for account endpoints
router.get('/:username', accountController.getAccountByName);
router.post('/', accountController.createAccount);
router.put('/:username', auth, accountController.updateAccount);
router.delete('/:username', auth, accountController.deleteAccount);

router.post('/login', accountController.login);

module.exports = router;
