const db = require('../config/database');
const crypto = require('crypto')
const jwt = require('jsonwebtoken');

// Generate a salt and hash for the accounts password
exports.hashPassword = function(password) {
    const salt = crypto.randomBytes(16).toString('hex');
    const hash = crypto.pbkdf2Sync(password, salt, 1000, 64, 'sha512').toString('hex');
    return { hash, salt };
};

// Create
exports.createAccount = function(account, callback) {
    const { hash, salt } = exports.hashPassword(account.password);
    const newUser = {
        username: account.username,
        hash: hash,
        salt: salt
    };

    db.query('INSERT INTO Account SET ?', newUser, callback);
};

// Read
exports.getAccountByName = function(name, callback) {
    db.query('SELECT * FROM Account WHERE username = ?',
         [name], callback);
};

// Update
exports.updateAccount = function(name, newName, callback) {
    db.query('UPDATE Account SET ? WHERE username = ?', 
        [newName, name], callback);
}

// Delete
exports.deleteAccount = function(name, callback) {
    db.query('DELETE FROM Account WHERE username = ?', [name], callback);
}

// Validate password
exports.validPassword = function(user, password) {
    user = user[0];
    console.log('User: ' + JSON.stringify(user, null, 2));
    const hash = crypto.pbkdf2Sync(password, user.salt, 1000, 64, 'sha512').toString('hex');
    return user.hash === hash;
};

// Generate a JWT
exports.generateJwt = function(user) {
    const expiry = new Date();
    expiry.setDate(expiry.getDate() + 7);

    return jwt.sign({
        name: user.name,
        exp: Math.floor(expiry.getTime() / 1000)
    }, process.env.JWT_SECRET);
};
