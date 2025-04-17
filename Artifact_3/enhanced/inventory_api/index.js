const express = require('express');
const itemRoutes = require('./routes/item_routes');
const accountRoutes = require('./routes/account_routes');

require('dotenv').config();

const app = express();
const PORT = process.env.PORT;

// Parser
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Routes
app.use('/items', itemRoutes);
app.use('/account', accountRoutes);

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});