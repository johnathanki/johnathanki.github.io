const jwt = require('jsonwebtoken');

// Authentication middleware to validate requests using a JWT
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    console.log("Received Authorization Header:", req.headers['authorization']);
    console.log("\n\nBody: " + req.body);
    
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        return res.status(401).json({ error: 'No token provided' });
    }

    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if (err) {
            return res.status(403).json({ error: 'Invalid or expired token' });
        }

        req.user = user;
        next();
    });
}

module.exports = authenticateToken;