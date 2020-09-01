const AccountReceivable = require('../models/AccountReceivable');
const { save, findAll } = require('../repository/AccountReceivableRepository');

const accountReceivableHandler = (producer) => (messages) => {
    const invoceDTO = JSON.parse(messages.value);
    const accountReceivable = new AccountReceivable(
        { ...invoceDTO }
    );
    save(accountReceivable);
    accountReceivablePusblisher(producer, accountReceivable);

};

const accountReceivablePusblisher = (producer, accountReceivable) => {

    const data = [
        { topic: 'accountReceivable', messages: JSON.stringify(accountReceivable) }
    ];

    producer.send(data, function (err, resp) {
        console.log(resp);
    });

    producer.on('error', function (err) {
        console.log(err);
    });
}


module.exports = accountReceivableHandler;