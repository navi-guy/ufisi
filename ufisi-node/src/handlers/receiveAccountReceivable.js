const AccountReceivable = require('../models/AccountReceivable');
const AccountReceivableRepository = require('../repository/AccountReceivableRepository');
const send = require('./sendAccountReceivable');

const receive = (producer, topicName) => (messages) => {
    const invoceDTO = JSON.parse(messages.value);
    const accountReceivable = new AccountReceivable(
        { ...invoceDTO }
    );
    AccountReceivableRepository.save(accountReceivable);
    send(producer, topicName, accountReceivable);
};

module.exports = receive;