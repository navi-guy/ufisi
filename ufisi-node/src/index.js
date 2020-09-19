const { db, bootstrap, kafka } = require('./config.json');
const Connection = require('./repository/Connection');
const recieveAccountReceivable = require('./handlers/receiveAccountReceivable');
const { KafkaClient, Consumer, Producer } = require('kafka-node');

async function main() {
    try {
        console.log("Inicia el Programa...!");
        await Connection.connect(db);
        console.log("Conexion a la Base de Datos OK!")
        const client = new KafkaClient({ kafkaHost: bootstrap.servers });
        const producer = new Producer(client);
        const consumer = new Consumer(client, [{ topic: kafka.topics[0].name }], { autoCommit: true });
        consumer.on('message', recieveAccountReceivable(producer, kafka.topics[1].name));
    } catch (err) {
        console.log(err);
    }

}

main();