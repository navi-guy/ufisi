const mongoose = require('mongoose');

const connect = ({ host, port, name }) => {
    return mongoose.connect(`mongodb://${host}:${port}/${name}`, { useNewUrlParser: true, useUnifiedTopology: true });
}

module.exports = {
    connect
};