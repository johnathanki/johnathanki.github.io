const User = require('../model/account');

// Controller to execute CRUD functions for accounts

// Create a new account
exports.createAccount = function(req, res) {
    User.createAccount(req.body, (err, result) => {
        if (err) {
            if (err.code === 'ER_DUP_ENTRY') {
                return res.status(409).json({ error: 'Account already exists' }); // 409 Conflict
            }
            throw err;
        }
        res.json({ message: 'Account created', result});
    });
}

// Fetch an existing account
exports.getAccountByName = function(req, res) {
    User.getAccountByName(req.params.username, (err, account) => {
        if (err)
            throw err;
        res.json(account);
    });
}

// Update an accounts username
exports.updateAccount = function(req, res) {
    User.updateAccount(req.params.username, req.params.newUsername, (err, result) => {
        if (err)
            throw err;
        res.json({ message: 'Username updated'});
    });
};

// Remove an existing account
exports.deleteAccount = function(req, res) {
    User.deleteAccount(req.params.username, (err, result) => {
        if (err)
            throw err;
        res.json({ message: 'User deleted'});
    });
};

// Handle login request
exports.login = function(req, res) {
    const { username, password } = req.body;

    User.getAccountByName(username, (err, user) => {
        if (err) return res.status(500).json({ error: 'Database error' });
        if (!user) return res.status(401).json({ error: 'Invalid username or password' });

        const isValid = User.validPassword(user, password);
        if (!isValid) {
            return res.status(401).json({ error: 'Invalid username or password' });
        }

        const token = User.generateJwt(user);
        res.json({ message: 'Login successful', token });
    });
};