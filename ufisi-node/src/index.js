require('./repository/connection');
const accountReceivableHandler = require('./handlers/accountReceivableHandler');
const { KafkaClient, Consumer, Producer } = require('kafka-node');

function main() {
    const client = new KafkaClient({ kafkaHost: 'ec2-100-25-165-143.compute-1.amazonaws.com:9092' });
    const producer = new Producer(client);
    seedInvoce(producer, { number: "1234", cliente: { nombre: "El navis", apellidos: "correlon" } });
    const consumer = new Consumer(client, [{ topic: 'invoces' }], { autoCommit: true });
    consumer.on('message', accountReceivableHandler(producer));
}

function seedInvoce(producer, invoce) {
    producer.on('ready', function () {
        const data = [
            { topic: 'invoces', messages: JSON.stringify(invoce) }
        ];

        producer.send(data, function (err, resp) {
        })
    });

    producer.on('error', function (err) {
        console.log(err);
    });
}

main();