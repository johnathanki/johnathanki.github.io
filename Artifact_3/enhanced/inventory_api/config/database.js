const mysql = require('mysql2');

require('dotenv').config();

// Establish MySQL connection
const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    database: process.env.DB_DATABASE,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD
});

connection.connect((err) => {
    if (err)
        throw err;
    console.log("Connected to MySQL DB");
});

module.exports = connection;