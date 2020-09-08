const { db, bootstrap, kafka } = require('./config.json');
const Connection = require('./repository/Connection');
const recieveAccountReceivable = require('./handlers/receiveAccountReceivable');
const { KafkaClient, Consumer, Producer } = require('kafka-node');

async function main() {
    try {
        await Connection.connect(db);
        const client = new KafkaClient({ kafkaHost: bootstrap.servers });
        const producer = new Producer(client);
        seedInvoce(producer, { number: "1234", cliente: { nombre: "El benjas", apellidos: "pelucon" } });
        const consumer = new Consumer(client, [{ topic: kafka.topics[0].name }], { autoCommit: true });
        consumer.on('message', recieveAccountReceivable(producer, kafka.topics[1].name));
    } catch (err) {
        console.log(err);
    }

}

function seedInvoce(producer, invoce) {
    producer.on('ready', function () {
        const data = [
            { topic: 'facturacion', messages: JSON.stringify(invoce) }
        ];

        producer.send(data, function (err, resp) {
            console.log(resp)

        })
    });

    producer.on('error', function (err) {
        console.log(err);
    });
}


main();