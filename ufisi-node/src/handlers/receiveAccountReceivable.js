const AccountReceivable = require('../models/AccountReceivable');
const AccountReceivableRepository = require('../repository/AccountReceivableRepository');
const send = require('./sendAccountReceivable');

const receive = (producer, topicName) => (messages) => {
    const invoceDTO = JSON.parse(messages.value);
    console.log("Ha llegado un mensaje desde el topico"+ topicName+":\n " + messages.value)
    const accountReceivable = new AccountReceivable(
        { ...invoceDTO }
    );
    AccountReceivableRepository.save(accountReceivable);
    send(producer, topicName, accountReceivable);
};

module.exports = receive;