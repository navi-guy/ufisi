const { Schema, model } = require('mongoose');

const AccountReceivableSchema = new Schema({
    invoce: Object,
    paymentDay: Date,
    state: String
});

const AccountReceivableModel = model('AccountReceivableModel', AccountReceivableSchema);

const save = (accountReceivable) => {
    const  model = new AccountReceivableModel({ ...accountReceivable });
    model.save();
} 

const findAll = () => {
    AccountReceivableModel.find({},function (err, accountReceivable) {
        if (err) return console.error(err);
        console.log(accountReceivable);
    })
} 

module.exports = {
    save,
    findAll
}